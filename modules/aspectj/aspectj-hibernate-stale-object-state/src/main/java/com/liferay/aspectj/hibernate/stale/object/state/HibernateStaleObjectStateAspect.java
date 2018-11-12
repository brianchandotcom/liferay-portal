/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
import org.hibernate.event.DeleteEvent;
import org.hibernate.event.MergeEvent;
import org.hibernate.event.SaveOrUpdateEvent;

/**
 * @author Preston Crary
 */
@Aspect
@SuppressAjWarnings("adviceDidNotMatch")
public class HibernateStaleObjectStateAspect {

	@AfterThrowing(
		throwing = "sose",
		value = "execution(void org.hibernate.event.def.DefaultMergeEventListener.onMerge(org.hibernate.event.MergeEvent)) && args(mergeEvent)"
	)
	public void suppressMergeFailureCause(
		MergeEvent mergeEvent, StaleObjectStateException sose) {

		_suppressFailureCause(mergeEvent.getOriginal(), sose);
	}

	@AfterThrowing(
		throwing = "ode",
		value = "execution(void org.hibernate.event.SaveOrUpdateEventListener.onSaveOrUpdate(org.hibernate.event.SaveOrUpdateEvent)) && args(saveOrUpdateEvent)"
	)
	public void suppressUpdateFailureCause(
		SaveOrUpdateEvent saveOrUpdateEvent, ObjectDeletedException ode) {

		_suppressFailureCause(saveOrUpdateEvent.getObject(), ode);
	}

	@AfterReturning(
		"execution(void org.hibernate.event.DeleteEventListener.onDelete(" +
			"org.hibernate.event.DeleteEvent)) && args(deleteEvent)"
	)
	public void trackDeleteEvent(DeleteEvent deleteEvent) {
		_trackEvent("Delete", deleteEvent.getObject());
	}

	@AfterReturning(
		"execution(void org.hibernate.event.def.DefaultMergeEventListener." +
			"onMerge(org.hibernate.event.MergeEvent)) && args(mergeEvent)"
	)
	public void trackMergeEvent(MergeEvent mergeEvent) {
		_trackEvent("Merge", mergeEvent.getOriginal());
	}

	@AfterReturning(
		"execution(void org.hibernate.event.SaveOrUpdateEventListener." +
			"onSaveOrUpdate(org.hibernate.event.SaveOrUpdateEvent)) &&" +
				"args(saveOrUpdateEvent)"
	)
	public void trackSaveOrUpdateEvent(SaveOrUpdateEvent saveOrUpdateEvent) {
		_trackEvent("SaveOrUpdate", saveOrUpdateEvent.getObject());
	}

	private void _suppressFailureCause(Object object, HibernateException he) {
		if (!(object instanceof MVCCModel)) {
			return;
		}

		he.addSuppressed(_events.get(new EventKey((BaseModel<?>)object)));
	}

	private void _trackEvent(String eventType, Object object) {
		if (!(object instanceof MVCCModel)) {
			return;
		}

		Exception exception = new Exception(
			eventType + " record for " + object);

		Exception previousException = _events.put(
			new EventKey((BaseModel<?>)object), exception);

		if (previousException != null) {
			exception.addSuppressed(previousException);
		}
	}

	private final Map<EventKey, Exception> _events = new ConcurrentHashMap<>();

	private static class EventKey {

		@Override
		public boolean equals(Object obj) {
			EventKey eventKey = (EventKey)obj;

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