import renderApp from './index.es'
import '../css/main.scss'
import 'clay-css/lib/css/atlas.css'

renderApp('app', {
    "initialQuery": "",
    "inputId": "_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionFilteruser",
    "modelLabel": "User",
    "properties": [
      {
        "label": "Ancestor Organization IDs",
        "name": "ancestorOrganizationIds",
        "type": "string"
      },
      {
        "label": "Class PK",
        "name": "classPK",
        "type": "string"
      },
      {
        "label": "Company ID",
        "name": "companyId",
        "type": "string"
      },
      {
        "label": "Date Modified",
        "name": "dateModified",
        "type": "string"
      },
      {
        "label": "Email Address",
        "name": "emailAddress",
        "type": "string"
      },
      {
        "label": "First Name",
        "name": "firstName",
        "type": "string"
      },
      {
        "label": "Group ID",
        "name": "groupId",
        "type": "string"
      },
      {
        "label": "Group IDs",
        "name": "groupIds",
        "type": "string"
      },
      {
        "label": "Job Title",
        "name": "jobTitle",
        "type": "string"
      },
      {
        "label": "Last Name",
        "name": "lastName",
        "type": "string"
      },
      {
        "label": "Organization Count",
        "name": "organizationCount",
        "type": "string"
      },
      {
        "label": "Organization IDs",
        "name": "organizationIds",
        "type": "string"
      },
      {
        "label": "Role IDs",
        "name": "roleIds",
        "type": "string"
      },
      {
        "label": "Scope Group ID",
        "name": "scopeGroupId",
        "type": "string"
      },
      {
        "label": "Screen Name",
        "name": "screenName",
        "type": "string"
      },
      {
        "label": "Team IDs",
        "name": "teamIds",
        "type": "string"
      },
      {
        "label": "User Group IDs",
        "name": "userGroupIds",
        "type": "string"
      },
      {
        "label": "User ID",
        "name": "userId",
        "type": "string"
      },
      {
        "label": "User Name",
        "name": "userName",
        "type": "string"
      }
    ]
  }, {
    "assetsPath": "assets",
    "spritemap": "lexicon/icons.svg"
  })