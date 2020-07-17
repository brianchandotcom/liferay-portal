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

package com.liferay.fragment.service;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link FragmentEntryLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLinkLocalService
 * @generated
 */
public class FragmentEntryLinkLocalServiceWrapper
	implements FragmentEntryLinkLocalService,
			   ServiceWrapper<FragmentEntryLinkLocalService> {

	public FragmentEntryLinkLocalServiceWrapper(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
	}

	/**
	 * Adds the fragment entry link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryLink the fragment entry link
	 * @return the fragment entry link that was added
	 */
	@Override
	public FragmentEntryLink addFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink) {

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			fragmentEntryLink);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addFragmentEntryLink(long, long, long, long, long, long,
	 String, String, String, String, String, String, int, String,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntryLink addFragmentEntryLink(
			long userId, long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long segmentsExperienceId, long classNameId,
			long classPK, String css, String html, String js,
			String configuration, String editableValues, String namespace,
			int position, String rendererKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			userId, groupId, originalFragmentEntryLinkId, fragmentEntryId,
			segmentsExperienceId, classNameId, classPK, css, html, js,
			configuration, editableValues, namespace, position, rendererKey,
			serviceContext);
	}

	@Override
	public FragmentEntryLink addFragmentEntryLink(
			long userId, long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long segmentsExperienceId, long plid,
			String css, String html, String js, String configuration,
			String editableValues, String namespace, int position,
			String rendererKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			userId, groupId, originalFragmentEntryLinkId, fragmentEntryId,
			segmentsExperienceId, plid, css, html, js, configuration,
			editableValues, namespace, position, rendererKey, serviceContext);
	}

	/**
	 * Creates a new fragment entry link with the primary key. Does not add the fragment entry link to the database.
	 *
	 * @param fragmentEntryLinkId the primary key for the new fragment entry link
	 * @return the new fragment entry link
	 */
	@Override
	public FragmentEntryLink createFragmentEntryLink(long fragmentEntryLinkId) {
		return _fragmentEntryLinkLocalService.createFragmentEntryLink(
			fragmentEntryLinkId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the fragment entry link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryLink the fragment entry link
	 * @return the fragment entry link that was removed
	 */
	@Override
	public FragmentEntryLink deleteFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink) {

		return _fragmentEntryLinkLocalService.deleteFragmentEntryLink(
			fragmentEntryLink);
	}

	/**
	 * Deletes the fragment entry link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryLinkId the primary key of the fragment entry link
	 * @return the fragment entry link that was removed
	 * @throws PortalException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink deleteFragmentEntryLink(long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.deleteFragmentEntryLink(
			fragmentEntryLinkId);
	}

	@Override
	public void deleteFragmentEntryLinks(long groupId) {
		_fragmentEntryLinkLocalService.deleteFragmentEntryLinks(groupId);
	}

	@Override
	public void deleteFragmentEntryLinks(long[] fragmentEntryLinkIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_fragmentEntryLinkLocalService.deleteFragmentEntryLinks(
			fragmentEntryLinkIds);
	}

	@Override
	public java.util.List<FragmentEntryLink>
		deleteLayoutPageTemplateEntryFragmentEntryLinks(
			long groupId, long plid) {

		return _fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(groupId, plid);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #deleteLayoutPageTemplateEntryFragmentEntryLinks(long, long)}
	 */
	@Deprecated
	@Override
	public java.util.List<FragmentEntryLink>
		deleteLayoutPageTemplateEntryFragmentEntryLinks(
			long groupId, long classNameId, long classPK) {

		return _fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				groupId, classNameId, classPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _fragmentEntryLinkLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fragmentEntryLinkLocalService.dynamicQuery();
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

		return _fragmentEntryLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl</code>.
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

		return _fragmentEntryLinkLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl</code>.
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

		return _fragmentEntryLinkLocalService.dynamicQuery(
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

		return _fragmentEntryLinkLocalService.dynamicQueryCount(dynamicQuery);
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

		return _fragmentEntryLinkLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public FragmentEntryLink fetchFragmentEntryLink(long fragmentEntryLinkId) {
		return _fragmentEntryLinkLocalService.fetchFragmentEntryLink(
			fragmentEntryLinkId);
	}

	/**
	 * Returns the fragment entry link matching the UUID and group.
	 *
	 * @param uuid the fragment entry link's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchFragmentEntryLinkByUuidAndGroupId(
		String uuid, long groupId) {

		return _fragmentEntryLinkLocalService.
			fetchFragmentEntryLinkByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _fragmentEntryLinkLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<FragmentEntryLink>
		getAllFragmentEntryLinksByFragmentEntryId(
			long groupId, long fragmentEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink>
				orderByComparator) {

		return _fragmentEntryLinkLocalService.
			getAllFragmentEntryLinksByFragmentEntryId(
				groupId, fragmentEntryId, start, end, orderByComparator);
	}

	@Override
	public int getAllFragmentEntryLinksCountByFragmentEntryId(
		long groupId, long fragmentEntryId) {

		return _fragmentEntryLinkLocalService.
			getAllFragmentEntryLinksCountByFragmentEntryId(
				groupId, fragmentEntryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFragmentEntryLinksCountByPlid(long, long)}
	 */
	@Deprecated
	@Override
	public int getClassedModelFragmentEntryLinksCount(
		long groupId, long classNameId, long classPK) {

		return _fragmentEntryLinkLocalService.
			getClassedModelFragmentEntryLinksCount(
				groupId, classNameId, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _fragmentEntryLinkLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	/**
	 * Returns the fragment entry link with the primary key.
	 *
	 * @param fragmentEntryLinkId the primary key of the fragment entry link
	 * @return the fragment entry link
	 * @throws PortalException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink getFragmentEntryLink(long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.getFragmentEntryLink(
			fragmentEntryLinkId);
	}

	/**
	 * Returns the fragment entry link matching the UUID and group.
	 *
	 * @param uuid the fragment entry link's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment entry link
	 * @throws PortalException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink getFragmentEntryLinkByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.
			getFragmentEntryLinkByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the fragment entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of fragment entry links
	 */
	@Override
	public java.util.List<FragmentEntryLink> getFragmentEntryLinks(
		int start, int end) {

		return _fragmentEntryLinkLocalService.getFragmentEntryLinks(start, end);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getAllFragmentEntryLinksByFragmentEntryId(long, long, int,
	 int, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public java.util.List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long fragmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink>
			orderByComparator) {

		return _fragmentEntryLinkLocalService.getFragmentEntryLinks(
			groupId, fragmentEntryId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFragmentEntryLinksByPlid(long, long)}
	 */
	@Deprecated
	@Override
	public java.util.List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long classNameId, long classPK) {

		return _fragmentEntryLinkLocalService.getFragmentEntryLinks(
			groupId, classNameId, classPK);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getLayoutPageTemplateFragmentEntryLinksByFragmentEntryId(
	 long, long, int, int, int, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public java.util.List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink>
			orderByComparator) {

		return _fragmentEntryLinkLocalService.getFragmentEntryLinks(
			groupId, fragmentEntryId, classNameId, layoutPageTemplateType,
			start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFragmentEntryLinks(long, long, int, int,
	 OrderByComparator)}
	 */
	@Deprecated
	@Override
	public java.util.List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long fragmentEntryId, long classNameId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink>
			orderByComparator) {

		return _fragmentEntryLinkLocalService.getFragmentEntryLinks(
			groupId, fragmentEntryId, classNameId, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<FragmentEntryLink> getFragmentEntryLinks(
		String rendererKey) {

		return _fragmentEntryLinkLocalService.getFragmentEntryLinks(
			rendererKey);
	}

	@Override
	public java.util.List<FragmentEntryLink>
		getFragmentEntryLinksByFragmentEntryId(long fragmentEntryId) {

		return _fragmentEntryLinkLocalService.
			getFragmentEntryLinksByFragmentEntryId(fragmentEntryId);
	}

	@Override
	public java.util.List<FragmentEntryLink> getFragmentEntryLinksByPlid(
		long groupId, long plid) {

		return _fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
			groupId, plid);
	}

	@Override
	public java.util.List<FragmentEntryLink>
		getFragmentEntryLinksBySegmentsExperienceId(
			long groupId, long segmentsExperienceId, long plid) {

		return _fragmentEntryLinkLocalService.
			getFragmentEntryLinksBySegmentsExperienceId(
				groupId, segmentsExperienceId, plid);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFragmentEntryLinksBySegmentsExperienceId(long, long,
	 long)}
	 */
	@Deprecated
	@Override
	public java.util.List<FragmentEntryLink>
		getFragmentEntryLinksBySegmentsExperienceId(
			long groupId, long segmentsExperienceId, long classNameId,
			long classPK) {

		return _fragmentEntryLinkLocalService.
			getFragmentEntryLinksBySegmentsExperienceId(
				groupId, segmentsExperienceId, classNameId, classPK);
	}

	/**
	 * Returns all the fragment entry links matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment entry links
	 * @param companyId the primary key of the company
	 * @return the matching fragment entry links, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<FragmentEntryLink>
		getFragmentEntryLinksByUuidAndCompanyId(String uuid, long companyId) {

		return _fragmentEntryLinkLocalService.
			getFragmentEntryLinksByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of fragment entry links matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment entry links
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching fragment entry links, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<FragmentEntryLink>
		getFragmentEntryLinksByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink>
				orderByComparator) {

		return _fragmentEntryLinkLocalService.
			getFragmentEntryLinksByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of fragment entry links.
	 *
	 * @return the number of fragment entry links
	 */
	@Override
	public int getFragmentEntryLinksCount() {
		return _fragmentEntryLinkLocalService.getFragmentEntryLinksCount();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getAllFragmentEntryLinksCountByFragmentEntryId(long, long)}
	 */
	@Deprecated
	@Override
	public int getFragmentEntryLinksCount(long groupId, long fragmentEntryId) {
		return _fragmentEntryLinkLocalService.getFragmentEntryLinksCount(
			groupId, fragmentEntryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFragmentEntryLinksCount(long, long)}
	 */
	@Deprecated
	@Override
	public int getFragmentEntryLinksCount(
		long groupId, long fragmentEntryId, long classNameId) {

		return _fragmentEntryLinkLocalService.getFragmentEntryLinksCount(
			groupId, fragmentEntryId, classNameId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getLayoutPageTemplateFragmentEntryLinksCountByFragmentEntryId(
	 long, long, int)}
	 */
	@Deprecated
	@Override
	public int getFragmentEntryLinksCount(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateType) {

		return _fragmentEntryLinkLocalService.getFragmentEntryLinksCount(
			groupId, fragmentEntryId, classNameId, layoutPageTemplateType);
	}

	@Override
	public int getFragmentEntryLinksCountByFragmentEntryId(
		long fragmentEntryId) {

		return _fragmentEntryLinkLocalService.
			getFragmentEntryLinksCountByFragmentEntryId(fragmentEntryId);
	}

	@Override
	public int getFragmentEntryLinksCountByPlid(long groupId, long plid) {
		return _fragmentEntryLinkLocalService.getFragmentEntryLinksCountByPlid(
			groupId, plid);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _fragmentEntryLinkLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<FragmentEntryLink>
		getLayoutFragmentEntryLinksByFragmentEntryId(
			long groupId, long fragmentEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink>
				orderByComparator) {

		return _fragmentEntryLinkLocalService.
			getLayoutFragmentEntryLinksByFragmentEntryId(
				groupId, fragmentEntryId, start, end, orderByComparator);
	}

	@Override
	public int getLayoutFragmentEntryLinksCountByFragmentEntryId(
		long groupId, long fragmentEntryId) {

		return _fragmentEntryLinkLocalService.
			getLayoutFragmentEntryLinksCountByFragmentEntryId(
				groupId, fragmentEntryId);
	}

	@Override
	public java.util.List<FragmentEntryLink>
		getLayoutPageTemplateFragmentEntryLinksByFragmentEntryId(
			long groupId, long fragmentEntryId, int layoutPageTemplateType,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink>
				orderByComparator) {

		return _fragmentEntryLinkLocalService.
			getLayoutPageTemplateFragmentEntryLinksByFragmentEntryId(
				groupId, fragmentEntryId, layoutPageTemplateType, start, end,
				orderByComparator);
	}

	@Override
	public int getLayoutPageTemplateFragmentEntryLinksCountByFragmentEntryId(
		long groupId, long fragmentEntryId, int layoutPageTemplateType) {

		return _fragmentEntryLinkLocalService.
			getLayoutPageTemplateFragmentEntryLinksCountByFragmentEntryId(
				groupId, fragmentEntryId, layoutPageTemplateType);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fragmentEntryLinkLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void updateClassedModel(long plid) {
		_fragmentEntryLinkLocalService.updateClassedModel(plid);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateClassedModel(long)}
	 */
	@Deprecated
	@Override
	public void updateClassedModel(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_fragmentEntryLinkLocalService.updateClassedModel(classNameId, classPK);
	}

	/**
	 * Updates the fragment entry link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryLink the fragment entry link
	 * @return the fragment entry link that was updated
	 */
	@Override
	public FragmentEntryLink updateFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink) {

		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(
			fragmentEntryLink);
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(
			fragmentEntryLinkId, position);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFragmentEntryLink(long, long, long, long, long,
	 String, String, String, String, String, String, int,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long userId, long fragmentEntryLinkId,
			long originalFragmentEntryLinkId, long fragmentEntryId,
			long classNameId, long classPK, String css, String html, String js,
			String configuration, String editableValues, String namespace,
			int position,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(
			userId, fragmentEntryLinkId, originalFragmentEntryLinkId,
			fragmentEntryId, classNameId, classPK, css, html, js, configuration,
			editableValues, namespace, position, serviceContext);
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long userId, long fragmentEntryLinkId,
			long originalFragmentEntryLinkId, long fragmentEntryId, long plid,
			String css, String html, String js, String configuration,
			String editableValues, String namespace, int position,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(
			userId, fragmentEntryLinkId, originalFragmentEntryLinkId,
			fragmentEntryId, plid, css, html, js, configuration, editableValues,
			namespace, position, serviceContext);
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues);
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues,
			boolean updateClassedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues, updateClassedModel);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFragmentEntryLinks(long, long, long, long[], String,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public void updateFragmentEntryLinks(
			long userId, long groupId, long classNameId, long classPK,
			long[] fragmentEntryIds, String editableValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			userId, groupId, classNameId, classPK, fragmentEntryIds,
			editableValues, serviceContext);
	}

	@Override
	public void updateFragmentEntryLinks(
			long userId, long groupId, long plid, long[] fragmentEntryIds,
			String editableValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			userId, groupId, plid, fragmentEntryIds, editableValues,
			serviceContext);
	}

	@Override
	public void updateFragmentEntryLinks(
			java.util.Map<Long, String> fragmentEntryLinksEditableValuesMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		_fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			fragmentEntryLinksEditableValuesMap);
	}

	@Override
	public void updateLatestChanges(long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_fragmentEntryLinkLocalService.updateLatestChanges(fragmentEntryLinkId);
	}

	@Override
	public CTPersistence<FragmentEntryLink> getCTPersistence() {
		return _fragmentEntryLinkLocalService.getCTPersistence();
	}

	@Override
	public Class<FragmentEntryLink> getModelClass() {
		return _fragmentEntryLinkLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<FragmentEntryLink>, R, E>
				updateUnsafeFunction)
		throws E {

		return _fragmentEntryLinkLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public FragmentEntryLinkLocalService getWrappedService() {
		return _fragmentEntryLinkLocalService;
	}

	@Override
	public void setWrappedService(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
	}

	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

}