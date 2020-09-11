# What are the Breaking Changes for Liferay Commerce 3.0?

This document presents a chronological list of changes that break existing
functionality, APIs, or contracts with third party Liferay Commerce developers or users.
We try our best to minimize these disruptions, but sometimes they are
unavoidable.

Here are some of the types of changes documented in this file:

* Functionality that is removed or replaced
* API incompatibilities: Changes to public Java or JavaScript APIs
* Changes to context variables available to templates
* Changes in CSS classes available to Liferay themes and portlets
* Configuration changes: Changes in configuration files, like
  `com.liferay.commerce.*.cfg` etc.
* Execution requirements: Java version, J2EE Version, browser versions, etc.
* Deprecations or end of support: For example, warning that a certain
  feature or API will be dropped in an upcoming version.
* Recommendations: For example, recommending using a newly introduced API that
  replaces an old API, in spite of the old API being kept in Liferay Portal for
  backwards compatibility.

*This document has been reviewed through commit .*

## Breaking Changes Contribution Guidelines

Each change must have a brief descriptive title and contain the following
information:

* **[Title]** Provide a brief descriptive title. Use past tense and follow
  the capitalization rules from
  <http://en.wikibooks.org/wiki/Basic_Book_Design/Capitalizing_Words_in_Titles>.
* **Date:** Specify the date you submitted the change. Format the date as
  *YYYY-MMM-DD* (e.g., 2014-Feb-25).
* **JIRA Ticket:** Reference the related JIRA ticket (e.g., LPS-12345)
  (Optional).
* **What changed?** Identify the affected component and the type of change that
  was made.
* **Who is affected?** Are end-users affected? Are developers affected? If the
  only affected people are those using a certain feature or API, say so.
* **How should I update my code?** Explain any client code changes required.
* **Why was this change made?** Explain the reason for the change. If
  applicable, justify why the breaking change was made instead of following a
  deprecation process.

Here's the template to use for each breaking change (note how it ends with a
horizontal rule):

```
### Title
- **Date:**
- **JIRA Ticket:**

#### What changed?

#### Who is affected?

#### How should I update my code?

#### Why was this change made?

---------------------------------------
```

**80 Columns Rule:** Text should not exceed 80 columns. Keeping text within 80
columns makes it easier to see the changes made between different versions of
the document. Titles, links, and tables are exempt from this rule. Code samples
must follow the column rules specified in Liferay's
[Development Style](http://www.liferay.com/community/wiki/-/wiki/Main/Liferay+development+style).

The remaining content of this document consists of the breaking changes listed
in ascending chronological order.

## Breaking Changes List

### Files Moved
- **Date:** 2020-Aug-20
- **JIRA Ticket:** [COMMERCE-4052](https://issues.liferay.com/browse/COMMERCE-4052)

#### What changed?

Files listed below have moved:

- `com.liferay.commerce.application.admin.web.servlet.taglib.ui.
CommerceApplicationBrandScreenNavigationConstants;`
- `com.liferay.commerce.application.internal.security.permission.resource.definition.
CommerceApplicationBrandModelResourcePermission;`
- `com.liferay.commerce.application.internal.security.permission.resource.definition.
CommerceApplicationModelModelResourcePermission;`
- `com.liferay.commerce.bom.admin.web.internal.servlet.taglib.ui.
CommerceBOMDefinitionScreenNavigationConstants;`
- `com.liferay.commerce.bom.api.model.CommerceBOMFolderConstants;`
- `com.liferay.commerce.bom.admin.web.internal.servlet.taglib.ui.
CommerceBOMFolderScreenNavigationConstants;`
- `com.liferay.commerce.bom.internal.security.permission.resource.definition.
CommerceBOMDefinitionModelResourcePermission;`
- `com.liferay.commerce.bom.internal.security.permission.resource.definition.
CommerceBOMFolderModelResourcePermission;`
- `com.liferay.commerce.machine.learning.internal.gateway.
CommerceMLJobStateConstants;`
- `com.liferay.commerce.organization.web.internal.servlet.taglib.ui.
CommerceOrganizationScreenNavigationConstants;`

#### Who is affected?

Anyone who references or uses these files.

#### How should I update my code?
Replace old references with the new package path.

New package paths:
- `com.liferay.commerce.application.admin.web.internal.constants.
CommerceApplicationBrandScreenNavigationConstants;`
- `com.liferay.commerce.application.internal.security.permission.resource.
CommerceApplicationBrandModelResourcePermission;`
- `com.liferay.commerce.application.internal.security.permission.resource.
CommerceApplicationModelModelResourcePermission;`
- `com.liferay.commerce.bom.admin.web.internal.constants.
CommerceBOMDefinitionScreenNavigationConstants;`
- `com.liferay.commerce.bom.api.constants.
CommerceBOMFolderConstants;`
- `com.liferay.commerce.bom.admin.web.internal.constants.
CommerceBOMFolderScreenNavigationConstants;`
- `com.liferay.commerce.bom.internal.security.permission.resource.
CommerceBOMDefinitionModelResourcePermission;`
- `com.liferay.commerce.bom.internal.security.permission.resource.
CommerceBOMFolderModelResourcePermission;`
- `com.liferay.commerce.machine.learning.internal.gateway.constants.
CommerceMLJobStateConstants;`
- `com.liferay.commerce.organization.web.internal.constants.
CommerceOrganizationScreenNavigationConstants;`

#### Why was this change made?

This was moved to follow Liferay coding pattern.

---------------------------------------