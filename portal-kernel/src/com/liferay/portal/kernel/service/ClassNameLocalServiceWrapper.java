/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link ClassNameLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ClassNameLocalService
 * @generated
 */
public class ClassNameLocalServiceWrapper
	implements ClassNameLocalService, ServiceWrapper<ClassNameLocalService> {

	public ClassNameLocalServiceWrapper() {
		this(null);
	}

	public ClassNameLocalServiceWrapper(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	/**
	 * Adds the class name to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClassNameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param className the class name
	 * @return the class name that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.ClassName addClassName(
		com.liferay.portal.kernel.model.ClassName className) {

		return _classNameLocalService.addClassName(className);
	}

	@Override
	public com.liferay.portal.kernel.model.ClassName addClassName(
		String value) {

		return _classNameLocalService.addClassName(value);
	}

	@Override
	public void checkClassNames() {
		_classNameLocalService.checkClassNames();
	}

	/**
	 * Creates a new class name with the primary key. Does not add the class name to the database.
	 *
	 * @param classNameId the primary key for the new class name
	 * @return the new class name
	 */
	@Override
	public com.liferay.portal.kernel.model.ClassName createClassName(
		long classNameId) {

		return _classNameLocalService.createClassName(classNameId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _classNameLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the class name from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClassNameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param className the class name
	 * @return the class name that was removed
	 */
	@Override
	public com.liferay.portal.kernel.model.ClassName deleteClassName(
		com.liferay.portal.kernel.model.ClassName className) {

		return _classNameLocalService.deleteClassName(className);
	}

	/**
	 * Deletes the class name with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClassNameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name that was removed
	 * @throws PortalException if a class name with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.ClassName deleteClassName(
			long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _classNameLocalService.deleteClassName(classNameId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _classNameLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _classNameLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _classNameLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _classNameLocalService.dynamicQuery();
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

		return _classNameLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ClassNameModelImpl</code>.
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

		return _classNameLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ClassNameModelImpl</code>.
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

		return _classNameLocalService.dynamicQuery(
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

		return _classNameLocalService.dynamicQueryCount(dynamicQuery);
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

		return _classNameLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.ClassName fetchByClassNameId(
		long classNameId) {

		return _classNameLocalService.fetchByClassNameId(classNameId);
	}

	@Override
	public com.liferay.portal.kernel.model.ClassName fetchClassName(
		long classNameId) {

		return _classNameLocalService.fetchClassName(classNameId);
	}

	@Override
	public com.liferay.portal.kernel.model.ClassName fetchClassName(
		String value) {

		return _classNameLocalService.fetchClassName(value);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _classNameLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the class name with the primary key.
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name
	 * @throws PortalException if a class name with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.ClassName getClassName(
			long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _classNameLocalService.getClassName(classNameId);
	}

	@Override
	public com.liferay.portal.kernel.model.ClassName getClassName(
		String value) {

		return _classNameLocalService.getClassName(value);
	}

	@Override
	public long getClassNameId(Class<?> clazz) {
		return _classNameLocalService.getClassNameId(clazz);
	}

	@Override
	public long getClassNameId(String value) {
		return _classNameLocalService.getClassNameId(value);
	}

	/**
	 * Returns a range of all the class names.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ClassNameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of class names
	 * @param end the upper bound of the range of class names (not inclusive)
	 * @return the range of class names
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.ClassName>
		getClassNames(int start, int end) {

		return _classNameLocalService.getClassNames(start, end);
	}

	/**
	 * Returns the number of class names.
	 *
	 * @return the number of class names
	 */
	@Override
	public int getClassNamesCount() {
		return _classNameLocalService.getClassNamesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _classNameLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _classNameLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _classNameLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public String getRegistryName() {
		return _classNameLocalService.getRegistryName();
	}

	@Override
	public void invalidate() {
		_classNameLocalService.invalidate();
	}

	/**
	 * Updates the class name in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClassNameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param className the class name
	 * @return the class name that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.ClassName updateClassName(
		com.liferay.portal.kernel.model.ClassName className) {

		return _classNameLocalService.updateClassName(className);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _classNameLocalService.getBasePersistence();
	}

	@Override
	public ClassNameLocalService getWrappedService() {
		return _classNameLocalService;
	}

	@Override
	public void setWrappedService(ClassNameLocalService classNameLocalService) {
		_classNameLocalService = classNameLocalService;
	}

	private ClassNameLocalService _classNameLocalService;

}