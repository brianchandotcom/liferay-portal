/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.internal.CTServiceRegistry;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTScore;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.base.CTScoreLocalServiceBaseImpl;
import com.liferay.change.tracking.service.persistence.CTCollectionPersistence;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ExceptionRetryAcceptor;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.spring.aop.Property;
import com.liferay.portal.kernel.spring.aop.Retry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTScore",
	service = AopService.class
)
@CTAware
public class CTScoreLocalServiceImpl extends CTScoreLocalServiceBaseImpl {

	@Override
	public CTScore addCTScore(long ctCollectionId) {
		CTCollection ctCollection = _ctCollectionPersistence.fetchByPrimaryKey(
			ctCollectionId);

		if (ctCollection == null) {
			return null;
		}

		List<CTEntry> ctEntries = _ctEntryLocalService.getCTCollectionCTEntries(
			ctCollectionId);

		int score = 0;

		for (CTEntry ctEntry : ctEntries) {
			score += _calculate(ctEntry.getModelClassNameId());
		}

		long ctScoreId = counterLocalService.increment(CTScore.class.getName());

		CTScore ctScore = ctScorePersistence.create(ctScoreId);

		ctScore.setCompanyId(ctCollection.getCompanyId());
		ctScore.setCtCollectionId(ctCollectionId);
		ctScore.setScore(score);

		return ctScorePersistence.update(ctScore);
	}

	@BufferedIncrement(incrementClass = NumberIncrement.class)
	@Override
	@Retry(
		acceptor = ExceptionRetryAcceptor.class,
		properties = {
			@Property(
				name = ExceptionRetryAcceptor.EXCEPTION_NAME,
				value = "org.hibernate.StaleObjectStateException"
			)
		}
	)
	public CTScore decrementScore(long ctCollectionId, long modelClassNameId) {
		return _updateScore(ctCollectionId, modelClassNameId, false);
	}

	public CTScore fetchCTScoreByCTCollectionId(long ctCollectionId) {
		return ctScorePersistence.fetchByCtCollectionId(ctCollectionId);
	}

	@BufferedIncrement(incrementClass = NumberIncrement.class)
	@Override
	@Retry(
		acceptor = ExceptionRetryAcceptor.class,
		properties = {
			@Property(
				name = ExceptionRetryAcceptor.EXCEPTION_NAME,
				value = "org.hibernate.StaleObjectStateException"
			)
		}
	)
	public CTScore incrementScore(long ctCollectionId, long modelClassNameId) {
		return _updateScore(ctCollectionId, modelClassNameId, true);
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_portalCache = (PortalCache<Long, Integer>)_multiVMPool.getPortalCache(
			CTScoreLocalServiceImpl.class.getName());
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();

		_portalCache.removeAll();
	}

	private int _calculate(long modelClassNameId) {
		Integer score = _portalCache.get(modelClassNameId);

		if (score != null) {
			return score;
		}

		score = 4;

		if (modelClassNameId == _classNameLocalService.getClassNameId(
				CTSContent.class)) {

			score += 20;
		}
		else if (modelClassNameId == _classNameLocalService.getClassNameId(
					JournalArticle.class)) {

			score++;
		}

		int countMultiplier = _countTable(modelClassNameId) / _COUNT_DIVISOR;

		if (countMultiplier == 0) {
			countMultiplier = 1;
		}

		score *= countMultiplier;

		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		if (dbType.equals(DBType.ORACLE) || dbType.equals(DBType.SQLSERVER)) {
			score *= 2;
		}

		_portalCache.put(modelClassNameId, score);

		return score;
	}

	private int _countTable(long modelClassNameId) {
		CTService<?> ctService = _ctServiceRegistry.getCTService(
			modelClassNameId);

		if (ctService == null) {
			return 0;
		}

		return ctService.updateWithUnsafeFunction(
			ctPersistence -> {
				Set<String> primaryKeyNames = ctPersistence.getCTColumnNames(
					CTColumnResolutionType.PK);

				if (primaryKeyNames.size() != 1) {
					throw new IllegalArgumentException(
						StringBundler.concat(
							"{primaryKeyNames=", primaryKeyNames,
							", tableName=", ctPersistence.getTableName(), "}"));
				}

				Iterator<String> iterator = primaryKeyNames.iterator();

				String primaryKeyName = iterator.next();

				Connection connection = _currentConnection.getConnection(
					ctPersistence.getDataSource());

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							StringBundler.concat(
								"select count(", primaryKeyName, ") from ",
								ctPersistence.getTableName()));
					ResultSet resultSet = preparedStatement.executeQuery()) {

					if (resultSet.next()) {
						return resultSet.getInt(1);
					}

					return 0;
				}
				catch (SQLException sqlException) {
					if (_log.isWarnEnabled()) {
						_log.warn(sqlException);
					}
				}

				return 0;
			});
	}

	private CTScore _updateScore(
		long ctCollectionId, long modelClassNameId, boolean increment) {

		CTScore ctScore = ctScorePersistence.fetchByCtCollectionId(
			ctCollectionId);

		if (ctScore == null) {
			return addCTScore(ctCollectionId);
		}

		int score = ctScore.getScore();

		if (increment) {
			score += _calculate(modelClassNameId);
		}
		else {
			score -= _calculate(modelClassNameId);
		}

		if (score < 0) {
			score = 0;
		}

		ctScore.setScore(score);

		return ctScorePersistence.update(ctScore);
	}

	private static final int _COUNT_DIVISOR = 50000000;

	private static final Log _log = LogFactoryUtil.getLog(
		CTScoreLocalServiceImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTCollectionPersistence _ctCollectionPersistence;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTServiceRegistry _ctServiceRegistry;

	@Reference
	private CurrentConnection _currentConnection;

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<Long, Integer> _portalCache;

}