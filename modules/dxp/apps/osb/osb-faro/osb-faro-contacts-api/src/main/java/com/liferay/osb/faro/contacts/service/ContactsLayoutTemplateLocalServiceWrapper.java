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

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link ContactsLayoutTemplateLocalService}.
 *
 * @author Shinn Lok
 * @see ContactsLayoutTemplateLocalService
 * @generated
 */
public class ContactsLayoutTemplateLocalServiceWrapper
	implements ContactsLayoutTemplateLocalService,
			   ServiceWrapper<ContactsLayoutTemplateLocalService> {

	public ContactsLayoutTemplateLocalServiceWrapper() {
		this(null);
	}

	public ContactsLayoutTemplateLocalServiceWrapper(
		ContactsLayoutTemplateLocalService contactsLayoutTemplateLocalService) {

		_contactsLayoutTemplateLocalService =
			contactsLayoutTemplateLocalService;
	}

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
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
		addContactsLayoutTemplate(
			com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
				contactsLayoutTemplate) {

		return _contactsLayoutTemplateLocalService.addContactsLayoutTemplate(
			contactsLayoutTemplate);
	}

	@Override
	public com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
		addContactsLayoutTemplate(
			long groupId, long userId, String headerContactsCardTemplateIds,
			String name, String settings, int type) {

		return _contactsLayoutTemplateLocalService.addContactsLayoutTemplate(
			groupId, userId, headerContactsCardTemplateIds, name, settings,
			type);
	}

	/**
	 * Creates a new contacts layout template with the primary key. Does not add the contacts layout template to the database.
	 *
	 * @param contactsLayoutTemplateId the primary key for the new contacts layout template
	 * @return the new contacts layout template
	 */
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
		createContactsLayoutTemplate(long contactsLayoutTemplateId) {

		return _contactsLayoutTemplateLocalService.createContactsLayoutTemplate(
			contactsLayoutTemplateId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsLayoutTemplateLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
		deleteContactsLayoutTemplate(
			com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
				contactsLayoutTemplate) {

		return _contactsLayoutTemplateLocalService.deleteContactsLayoutTemplate(
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
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
			deleteContactsLayoutTemplate(long contactsLayoutTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsLayoutTemplateLocalService.deleteContactsLayoutTemplate(
			contactsLayoutTemplateId);
	}

	@Override
	public void deleteContactsLayoutTemplates(long groupId) {
		_contactsLayoutTemplateLocalService.deleteContactsLayoutTemplates(
			groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsLayoutTemplateLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _contactsLayoutTemplateLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _contactsLayoutTemplateLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _contactsLayoutTemplateLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _contactsLayoutTemplateLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _contactsLayoutTemplateLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _contactsLayoutTemplateLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _contactsLayoutTemplateLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _contactsLayoutTemplateLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
		fetchContactsLayoutTemplate(long contactsLayoutTemplateId) {

		return _contactsLayoutTemplateLocalService.fetchContactsLayoutTemplate(
			contactsLayoutTemplateId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _contactsLayoutTemplateLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the contacts layout template with the primary key.
	 *
	 * @param contactsLayoutTemplateId the primary key of the contacts layout template
	 * @return the contacts layout template
	 * @throws PortalException if a contacts layout template with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
			getContactsLayoutTemplate(long contactsLayoutTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsLayoutTemplateLocalService.getContactsLayoutTemplate(
			contactsLayoutTemplateId);
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
	@Override
	public java.util.List
		<com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate>
			getContactsLayoutTemplates(int start, int end) {

		return _contactsLayoutTemplateLocalService.getContactsLayoutTemplates(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate>
			getContactsLayoutTemplates(
				long groupId, int type, int start, int end) {

		return _contactsLayoutTemplateLocalService.getContactsLayoutTemplates(
			groupId, type, start, end);
	}

	/**
	 * Returns the number of contacts layout templates.
	 *
	 * @return the number of contacts layout templates
	 */
	@Override
	public int getContactsLayoutTemplatesCount() {
		return _contactsLayoutTemplateLocalService.
			getContactsLayoutTemplatesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _contactsLayoutTemplateLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _contactsLayoutTemplateLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsLayoutTemplateLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
		updateContactsLayoutTemplate(
			com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
				contactsLayoutTemplate) {

		return _contactsLayoutTemplateLocalService.updateContactsLayoutTemplate(
			contactsLayoutTemplate);
	}

	@Override
	public com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate
			updateContactsLayoutTemplate(
				long contactsLayoutTemplateId,
				String headerContactsCardTemplateIds, String name,
				String settings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsLayoutTemplateLocalService.updateContactsLayoutTemplate(
			contactsLayoutTemplateId, headerContactsCardTemplateIds, name,
			settings);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _contactsLayoutTemplateLocalService.getBasePersistence();
	}

	@Override
	public ContactsLayoutTemplateLocalService getWrappedService() {
		return _contactsLayoutTemplateLocalService;
	}

	@Override
	public void setWrappedService(
		ContactsLayoutTemplateLocalService contactsLayoutTemplateLocalService) {

		_contactsLayoutTemplateLocalService =
			contactsLayoutTemplateLocalService;
	}

	private ContactsLayoutTemplateLocalService
		_contactsLayoutTemplateLocalService;

}