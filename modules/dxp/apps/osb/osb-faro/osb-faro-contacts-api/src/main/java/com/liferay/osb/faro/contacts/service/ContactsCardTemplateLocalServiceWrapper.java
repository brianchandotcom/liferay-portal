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
 * Provides a wrapper for {@link ContactsCardTemplateLocalService}.
 *
 * @author Shinn Lok
 * @see ContactsCardTemplateLocalService
 * @generated
 */
public class ContactsCardTemplateLocalServiceWrapper
	implements ContactsCardTemplateLocalService,
			   ServiceWrapper<ContactsCardTemplateLocalService> {

	public ContactsCardTemplateLocalServiceWrapper() {
		this(null);
	}

	public ContactsCardTemplateLocalServiceWrapper(
		ContactsCardTemplateLocalService contactsCardTemplateLocalService) {

		_contactsCardTemplateLocalService = contactsCardTemplateLocalService;
	}

	/**
	 * Adds the contacts card template to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactsCardTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contactsCardTemplate the contacts card template
	 * @return the contacts card template that was added
	 */
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsCardTemplate
		addContactsCardTemplate(
			com.liferay.osb.faro.contacts.model.ContactsCardTemplate
				contactsCardTemplate) {

		return _contactsCardTemplateLocalService.addContactsCardTemplate(
			contactsCardTemplate);
	}

	@Override
	public com.liferay.osb.faro.contacts.model.ContactsCardTemplate
		addContactsCardTemplate(
			long groupId, long userId, String name, String settings, int type) {

		return _contactsCardTemplateLocalService.addContactsCardTemplate(
			groupId, userId, name, settings, type);
	}

	/**
	 * Creates a new contacts card template with the primary key. Does not add the contacts card template to the database.
	 *
	 * @param contactsCardTemplateId the primary key for the new contacts card template
	 * @return the new contacts card template
	 */
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsCardTemplate
		createContactsCardTemplate(long contactsCardTemplateId) {

		return _contactsCardTemplateLocalService.createContactsCardTemplate(
			contactsCardTemplateId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsCardTemplateLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the contacts card template from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactsCardTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contactsCardTemplate the contacts card template
	 * @return the contacts card template that was removed
	 */
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsCardTemplate
		deleteContactsCardTemplate(
			com.liferay.osb.faro.contacts.model.ContactsCardTemplate
				contactsCardTemplate) {

		return _contactsCardTemplateLocalService.deleteContactsCardTemplate(
			contactsCardTemplate);
	}

	/**
	 * Deletes the contacts card template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactsCardTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contactsCardTemplateId the primary key of the contacts card template
	 * @return the contacts card template that was removed
	 * @throws PortalException if a contacts card template with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsCardTemplate
			deleteContactsCardTemplate(long contactsCardTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsCardTemplateLocalService.deleteContactsCardTemplate(
			contactsCardTemplateId);
	}

	@Override
	public void deleteContactsCardTemplates(long groupId) {
		_contactsCardTemplateLocalService.deleteContactsCardTemplates(groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsCardTemplateLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _contactsCardTemplateLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _contactsCardTemplateLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _contactsCardTemplateLocalService.dynamicQuery();
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

		return _contactsCardTemplateLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.contacts.model.impl.ContactsCardTemplateModelImpl</code>.
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

		return _contactsCardTemplateLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.contacts.model.impl.ContactsCardTemplateModelImpl</code>.
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

		return _contactsCardTemplateLocalService.dynamicQuery(
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

		return _contactsCardTemplateLocalService.dynamicQueryCount(
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

		return _contactsCardTemplateLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.faro.contacts.model.ContactsCardTemplate
		fetchContactsCardTemplate(long contactsCardTemplateId) {

		return _contactsCardTemplateLocalService.fetchContactsCardTemplate(
			contactsCardTemplateId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _contactsCardTemplateLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the contacts card template with the primary key.
	 *
	 * @param contactsCardTemplateId the primary key of the contacts card template
	 * @return the contacts card template
	 * @throws PortalException if a contacts card template with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsCardTemplate
			getContactsCardTemplate(long contactsCardTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsCardTemplateLocalService.getContactsCardTemplate(
			contactsCardTemplateId);
	}

	/**
	 * Returns a range of all the contacts card templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.contacts.model.impl.ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @return the range of contacts card templates
	 */
	@Override
	public java.util.List
		<com.liferay.osb.faro.contacts.model.ContactsCardTemplate>
			getContactsCardTemplates(int start, int end) {

		return _contactsCardTemplateLocalService.getContactsCardTemplates(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.osb.faro.contacts.model.ContactsCardTemplate>
			getContactsCardTemplates(String contactsCardTemplateIds) {

		return _contactsCardTemplateLocalService.getContactsCardTemplates(
			contactsCardTemplateIds);
	}

	/**
	 * Returns the number of contacts card templates.
	 *
	 * @return the number of contacts card templates
	 */
	@Override
	public int getContactsCardTemplatesCount() {
		return _contactsCardTemplateLocalService.
			getContactsCardTemplatesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _contactsCardTemplateLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _contactsCardTemplateLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsCardTemplateLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the contacts card template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactsCardTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contactsCardTemplate the contacts card template
	 * @return the contacts card template that was updated
	 */
	@Override
	public com.liferay.osb.faro.contacts.model.ContactsCardTemplate
		updateContactsCardTemplate(
			com.liferay.osb.faro.contacts.model.ContactsCardTemplate
				contactsCardTemplate) {

		return _contactsCardTemplateLocalService.updateContactsCardTemplate(
			contactsCardTemplate);
	}

	@Override
	public com.liferay.osb.faro.contacts.model.ContactsCardTemplate
			updateContactsCardTemplate(
				long contactsCardTemplateId, String name, String settings,
				int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactsCardTemplateLocalService.updateContactsCardTemplate(
			contactsCardTemplateId, name, settings, type);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _contactsCardTemplateLocalService.getBasePersistence();
	}

	@Override
	public ContactsCardTemplateLocalService getWrappedService() {
		return _contactsCardTemplateLocalService;
	}

	@Override
	public void setWrappedService(
		ContactsCardTemplateLocalService contactsCardTemplateLocalService) {

		_contactsCardTemplateLocalService = contactsCardTemplateLocalService;
	}

	private ContactsCardTemplateLocalService _contactsCardTemplateLocalService;

}