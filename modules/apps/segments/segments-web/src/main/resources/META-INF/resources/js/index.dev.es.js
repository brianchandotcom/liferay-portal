import '../css/main.scss';
import 'clay-css/lib/css/atlas.css';
import React from 'react';
import ReactDOM from 'react-dom';
import SegmentEdit from './components/segment_edit/SegmentEdit.es';
import ThemeContext from './ThemeContext.es';

const altProps = {
	contributors: [
		{
			conjunctionId: '',
			conjunctionInputId: '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionConjunctionuser',
			initialQuery: '(segmentationTeamMembers eq \'kevin\' and segmentationTeamMembers eq \'kevin\' and dateModified eq 2019-01-16T11:17:56.000Z)',
			inputId: '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionFilteruser',
			propertyKey: 'user'
		},
		{
			conjunctionId: '',
			conjunctionInputId: '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionConjunctionuser-organization',
			initialQuery: '(cookie/any(c:contains(c, \'key1=value1\')))',
			inputId: '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionFilteruser-organization',
			propertyKey: 'user-organization'
		}
	],
	editing: false,
	initialMembersCount: 0,
	initialSegmentActive: false,
	initialSegmentName: '',
	locale: 'en_US',
	portletNamespace: '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_',
	propertyGroups: [
		{
			entityName: 'User',
			name: 'User',
			properties: [
				{
					label: 'Ancestor Organization IDs',
					name: 'ancestorOrganizationIds',
					type: 'string'
				},
				{
					label: 'Segmentation Team Members',
					name: 'segmentationTeamMembers',
					options: [
						{
							label: 'Kevin',
							value: 'kevin'
						},
						{
							label: 'Angelo',
							value: 'angelo'
						},
						{
							label: 'David',
							value: 'david'
						},
						{
							label: 'Edu',
							value: 'edu'
						},
						{
							label: 'Andres',
							value: 'andres'
						}
					],
					type: 'string'
				},
				{
					label: 'Accepted Privacy',
					name: 'acceptedPrivacy',
					type: 'boolean'
				},
				{
					label: 'People',
					name: 'people',
					type: 'integer'
				},
				{
					label: 'Liters',
					name: 'liters',
					type: 'double'
				},
				{
					label: 'Class PK',
					name: 'classPK',
					type: 'string'
				},
				{
					label: 'Company ID',
					name: 'companyId',
					type: 'string'
				},
				{
					label: 'Date Modified',
					name: 'dateModified',
					type: 'date'
				},
				{
					label: 'Email Address',
					name: 'emailAddress',
					type: 'string'
				},
				{
					label: 'First Name',
					name: 'firstName',
					type: 'string'
				},
				{
					label: 'Group ID',
					name: 'groupId',
					type: 'string'
				},
				{
					label: 'Group IDs',
					name: 'groupIds',
					type: 'string'
				},
				{
					label: 'Job Title',
					name: 'jobTitle',
					type: 'string'
				},
				{
					label: 'Last Name',
					name: 'lastName',
					type: 'string'
				},
				{
					label: 'Organization IDs',
					name: 'organizationIds',
					type: 'string'
				},
				{
					label: 'Role IDs',
					name: 'roleIds',
					type: 'string'
				},
				{
					label: 'Scope Group ID',
					name: 'scopeGroupId',
					type: 'string'
				},
				{
					label: 'Screen Name',
					name: 'screenName',
					type: 'string'
				},
				{
					label: 'Team IDs',
					name: 'teamIds',
					type: 'string'
				},
				{
					label: 'User Group IDs',
					name: 'userGroupIds',
					type: 'string'
				},
				{
					label: 'User ID',
					name: 'userId',
					type: 'string'
				},
				{
					label: 'User Name',
					name: 'userName',
					type: 'string'
				}
			],
			propertyKey: 'user'
		},
		{
			entityName: 'Organization',
			name: 'Organization',
			properties: [
				{
					label: 'Class PK',
					name: 'classPK',
					type: 'string'
				},
				{
					label: 'Company ID',
					name: 'companyId',
					type: 'string'
				},
				{
					label: 'Date Modified',
					name: 'dateModified',
					type: 'date'
				},
				{
					label: 'Name',
					name: 'name',
					type: 'string'
				},
				{
					label: 'Cookie',
					name: 'cookie',
					type: 'collection'
				},
				{
					label: 'Name Tree Path',
					name: 'nameTreePath',
					type: 'string'
				},
				{
					label: 'Organization ID',
					name: 'organizationId',
					selectEntity: {
						id: '_com_liferay_users_admin_web_portlet_UsersAdminPortlet_selectOrganization',
						title: 'Select Organization',
						uri: 'http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_segments_web_internal_portlet_SegmentsPortlet&p_p_lifecycle=0&p_p_state=pop_up&p_p_mode=view&_com_liferay_segments_web_internal_portlet_SegmentsPortlet_mvcRenderCommandName=selectSegmentsEntryOrganizations&_com_liferay_segments_web_internal_portlet_SegmentsPortlet_segmentsEntryId=37008&p_p_auth=SsX9DxLY'
					},
					type: 'id'
				},
				{
					label: 'Parent Organization ID',
					name: 'parentOrganizationId',
					selectEntity: {
						id: '_com_liferay_users_admin_web_portlet_UsersAdminPortlet_selectOrganization',
						title: 'Select Organization',
						uri: 'http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_segments_web_internal_portlet_SegmentsPortlet&p_p_lifecycle=0&p_p_state=pop_up&p_p_mode=view&_com_liferay_segments_web_internal_portlet_SegmentsPortlet_mvcRenderCommandName=selectSegmentsEntryOrganizations&_com_liferay_segments_web_internal_portlet_SegmentsPortlet_segmentsEntryId=37008&p_p_auth=SsX9DxLY'
					},
					type: 'id'
				},
				{
					label: 'Tree Path',
					name: 'treePath',
					type: 'string'
				},
				{
					label: 'Type',
					name: 'type',
					type: 'string'
				}
			],
			propertyKey: 'user-organization'
		}
	],
	redirect: 'http://localhost:8080/group/guest/~/control_panel/manage/-/segments/entries?p_p_auth=1EwOzg1e'
};

const context = {
	assetsPath: 'assets',
	spritemap: '/o/admin-theme/images/lexicon/icons.svg'
};

ReactDOM.render(
	<ThemeContext.Provider value={context}>
		<div className="segments-root">
			<SegmentEdit {...altProps} />
		</div>
	</ThemeContext.Provider>,
	document.getElementById('app')
);