/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.model.CTScore;
import com.liferay.change.tracking.model.impl.CTScoreImpl;
import com.liferay.change.tracking.service.persistence.CTScoreFinder;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.Session;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTScoreFinder.class)
public class CTScoreFinderImpl
	extends CTScoreFinderBaseImpl implements CTScoreFinder {

	@Override
	public CTScore updateScore(long ctCollectionId, int score) {
		Session session = null;

		CTScore ctScore = null;

		try {
			session = openSession();

			ctScore = (CTScore)session.get(
				CTScoreImpl.class, ctCollectionId, LockMode.UPGRADE);

			if (ctScore == null) {
				return ctScore;
			}

			score = ctScore.getScore() + score;

			if (score < 0) {
				score = 0;
			}

			ctScore.setScore(score);

			ctScore = (CTScore)session.merge(ctScore);
		}
		finally {
			closeSession(session);
		}

		_entityCache.putResult(CTScoreImpl.class, ctScore, false, true);

		ctScore.resetOriginalValues();

		return ctScore;
	}

	@Reference
	private EntityCache _entityCache;

}