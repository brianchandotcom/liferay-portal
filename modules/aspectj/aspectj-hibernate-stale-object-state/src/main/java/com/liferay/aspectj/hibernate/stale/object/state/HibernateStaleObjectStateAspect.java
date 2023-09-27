/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.aspectj.hibernate.stale.object.state;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.SuppressAjWarnings;

import org.hibernate.HibernateException;
import org.hibernate.ObjectDeletedException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.event.spi.DeleteEvent;
import org.hibernate.event.spi.MergeEvent;
import org.hibernate.event.spi.SaveOrUpdateEvent;

/**
 * @author Preston Crary
 */
@Aspect
@SuppressAjWarnings("adviceDidNotMatch")
public class HibernateStaleObjectStateAspect {

	@AfterThrowing(
		throwing = "staleObjectStateException",
		value = "execution(void org.hibernate.event.internal.DefaultMergeEventListener.onMerge(org.hibernate.event.spi.MergeEvent)) && args(mergeEvent)"
	)
	public void suppressMergeFailureCause(
		MergeEvent mergeEvent,
		StaleObjectStateException staleObjectStateException) {

		_suppressFailureCause(
			mergeEvent.getOriginal(), staleObjectStateException);
	}

	@AfterThrowing(
		throwing = "objectDeletedException",
		value = "execution(void org.hibernate.event.spi.SaveOrUpdateEventListener.onSaveOrUpdate(org.hibernate.event.spi.SaveOrUpdateEvent)) && args(saveOrUpdateEvent)"
	)
	public void suppressUpdateFailureCause(
		SaveOrUpdateEvent saveOrUpdateEvent,
		ObjectDeletedException objectDeletedException) {

		_suppressFailureCause(
			saveOrUpdateEvent.getObject(), objectDeletedException);
	}

	@AfterReturning(
		"execution(void org.hibernate.event.spi.DeleteEventListener.onDelete(" +
			"org.hibernate.event.spi.DeleteEvent)) && args(deleteEvent)"
	)
	public void trackDeleteEvent(DeleteEvent deleteEvent) {
		_trackEvent("Delete", deleteEvent.getObject());
	}

	@AfterReturning(
		"execution(void org.hibernate.event.internal.DefaultMergeEventListener." +
			"onMerge(org.hibernate.event.spi.MergeEvent)) && args(mergeEvent)"
	)
	public void trackMergeEvent(MergeEvent mergeEvent) {
		_trackEvent("Merge", mergeEvent.getOriginal());
	}

	@AfterReturning(
		"execution(void org.hibernate.event.spi.SaveOrUpdateEventListener." +
			"onSaveOrUpdate(org.hibernate.event.spi.SaveOrUpdateEvent)) &&" +
				"args(saveOrUpdateEvent)"
	)
	public void trackSaveOrUpdateEvent(SaveOrUpdateEvent saveOrUpdateEvent) {
		_trackEvent("SaveOrUpdate", saveOrUpdateEvent.getObject());
	}

	private void _suppressFailureCause(
		Object object, HibernateException hibernateException) {

		if (!(object instanceof MVCCModel)) {
			return;
		}

		hibernateException.addSuppressed(
			_events.get(new EventKey((BaseModel<?>)object)));
	}

	private void _trackEvent(String eventType, Object object) {
		if (!(object instanceof MVCCModel)) {
			return;
		}

		Exception exception1 = new Exception(
			eventType + " record for " + object);

		Exception exception2 = _events.put(
			new EventKey((BaseModel<?>)object), exception1);

		if (exception2 != null) {
			exception1.addSuppressed(exception2);
		}
	}

	private final Map<EventKey, Exception> _events = new ConcurrentHashMap<>();

	private static class EventKey {

		@Override
		public boolean equals(Object object) {
			EventKey eventKey = (EventKey)object;

			if ((eventKey._mvccVersion == _mvccVersion) &&
				Objects.equals(eventKey._primaryKey, _primaryKey) &&
				Objects.equals(eventKey._modelClassName, _modelClassName)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _mvccVersion);

			hashCode = HashUtil.hash(hashCode, _primaryKey);
			hashCode = HashUtil.hash(hashCode, _modelClassName);

			return hashCode;
		}

		private EventKey(BaseModel<?> baseModel) {
			_modelClassName = baseModel.getModelClassName();
			_primaryKey = baseModel.getPrimaryKeyObj();

			MVCCModel mvccModel = (MVCCModel)baseModel;

			_mvccVersion = mvccModel.getMvccVersion();
		}

		private final String _modelClassName;
		private final long _mvccVersion;
		private final Serializable _primaryKey;

	}

}