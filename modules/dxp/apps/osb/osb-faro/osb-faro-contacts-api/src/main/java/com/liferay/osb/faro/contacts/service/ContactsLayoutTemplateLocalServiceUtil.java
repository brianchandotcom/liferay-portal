/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.contacts.service;

import com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ContactsLayoutTemplate. This utility wraps
 * <code>com.liferay.osb.faro.contacts.service.impl.ContactsLayoutTemplateLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Shinn Lok
 * @see ContactsLayoutTemplateLocalService
 * @generated
 */
public class ContactsLayoutTemplateLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.faro.contacts.service.impl.ContactsLayoutTemplateLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the contacts layout template to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactsLayoutTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contactsLayoutTemplate the contacts layout template
	 * @return the contacts layout template that was added
	 */
	public static ContactsLayoutTemplate addContactsLayoutTemplate(
		ContactsLayoutTemplate contactsLayoutTemplate) {

		return getService().addContactsLayoutTemplate(contactsLayoutTemplate);
	}

	public static ContactsLayoutTemplate addContactsLayoutTemplate(
		long groupId, long userId, String headerContactsCardTemplateIds,
		String name, String settings, int type) {

		return getService().addContactsLayoutTemplate(
			groupId, userId, headerContactsCardTemplateIds, name, settings,
			type);
	}

	/**
	 * Creates a new contacts layout template with the primary key. Does not add the contacts layout template to the database.
	 *
	 * @param contactsLayoutTemplateId the primary key for the new contacts layout template
	 * @return the new contacts layout template
	 */
	public static ContactsLayoutTemplate createContactsLayoutTemplate(
		long contactsLayoutTemplateId) {

		return getService().createContactsLayoutTemplate(
			contactsLayoutTemplateId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the contacts layout template from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactsLayoutTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contactsLayoutTemplate the contacts layout template
	 * @return the contacts layout template that was removed
	 */
	public static ContactsLayoutTemplate deleteContactsLayoutTemplate(
		ContactsLayoutTemplate contactsLayoutTemplate) {

		return getService().deleteContactsLayoutTemplate(
			contactsLayoutTemplate);
	}

	/**
	 * Deletes the contacts layout template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactsLayoutTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contactsLayoutTemplateId the primary key of the contacts layout template
	 * @return the contacts layout template that was removed
	 * @throws PortalException if a contacts layout template with the primary key could not be found
	 */
	public static ContactsLayoutTemplate deleteContactsLayoutTemplate(
			long contactsLayoutTemplateId)
		throws PortalException {

		return getService().deleteContactsLayoutTemplate(
			contactsLayoutTemplateId);
	}

	public static void deleteContactsLayoutTemplates(long groupId) {
		getService().deleteContactsLayoutTemplates(groupId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.contacts.model.impl.ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.contacts.model.impl.ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static ContactsLayoutTemplate fetchContactsLayoutTemplate(
		long contactsLayoutTemplateId) {

		return getService().fetchContactsLayoutTemplate(
			contactsLayoutTemplateId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the contacts layout template with the primary key.
	 *
	 * @param contactsLayoutTemplateId the primary key of the contacts layout template
	 * @return the contacts layout template
	 * @throws PortalException if a contacts layout template with the primary key could not be found
	 */
	public static ContactsLayoutTemplate getContactsLayoutTemplate(
			long contactsLayoutTemplateId)
		throws PortalException {

		return getService().getContactsLayoutTemplate(contactsLayoutTemplateId);
	}

	/**
	 * Returns a range of all the contacts layout templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.contacts.model.impl.ContactsLayoutTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts layout templates
	 * @param end the upper bound of the range of contacts layout templates (not inclusive)
	 * @return the range of contacts layout templates
	 */
	public static List<ContactsLayoutTemplate> getContactsLayoutTemplates(
		int start, int end) {

		return getService().getContactsLayoutTemplates(start, end);
	}

	public static List<ContactsLayoutTemplate> getContactsLayoutTemplates(
		long groupId, int type, int start, int end) {

		return getService().getContactsLayoutTemplates(
			groupId, type, start, end);
	}

	/**
	 * Returns the number of contacts layout templates.
	 *
	 * @return the number of contacts layout templates
	 */
	public static int getContactsLayoutTemplatesCount() {
		return getService().getContactsLayoutTemplatesCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the contacts layout template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactsLayoutTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contactsLayoutTemplate the contacts layout template
	 * @return the contacts layout template that was updated
	 */
	public static ContactsLayoutTemplate updateContactsLayoutTemplate(
		ContactsLayoutTemplate contactsLayoutTemplate) {

		return getService().updateContactsLayoutTemplate(
			contactsLayoutTemplate);
	}

	public static ContactsLayoutTemplate updateContactsLayoutTemplate(
			long contactsLayoutTemplateId, String headerContactsCardTemplateIds,
			String name, String settings)
		throws PortalException {

		return getService().updateContactsLayoutTemplate(
			contactsLayoutTemplateId, headerContactsCardTemplateIds, name,
			settings);
	}

	public static ContactsLayoutTemplateLocalService getService() {
		return _service;
	}

	public static void setService(ContactsLayoutTemplateLocalService service) {
		_service = service;
	}

	private static volatile ContactsLayoutTemplateLocalService _service;

}