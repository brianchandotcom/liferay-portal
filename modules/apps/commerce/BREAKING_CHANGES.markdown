# What are the Breaking Changes for Liferay Commerce 2.1?

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

*This document has been reviewed through commit ``.*

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

### `CPInstance` Entity Field `json` Is Replaced With New Entity `CPInstanceOptionValueRel`
- **Date:** 2020-Feb-25
- **JIRA Ticket:** [COMMERCE-2692](https://issues.liferay.com/browse/COMMERCE-2692)

#### What changed?

The CPInstance field `json` that holds product options configuration is replaced
with entity CPInstanceOptionValueRel.

#### Who is affected?

Commerce CPInstace Service layer API and Helper classes.

#### How should I update my code?

Internal parent module usage:
Use method
`com.liferay.commerce.product.service.impl.CPDefinitionOptionRelLocalServiceImpl
#getCPDefinitionOptionRelCPDefinitionOptionValueRelIds(long, java.lang.String)`
to supply option configuration for CPInstance creation.
Use method
`com.liferay.commerce.product.service.impl.
CPInstanceOptionValueRelLocalServiceImpl#matchesCPInstanceOptionValueRels(long,
java.util.Map<java.lang.Long,java.util.List<java.lang.Long>>)`
to compare a particular option combination with an existing instance option
combination.

Outter usage via CPInstanceHelper interface:
Use method
`com.liferay.commerce.product.internal.util.CPInstanceHelperImpl#
getCPDefinitionOptionRelsMap(long, java.lang.String)`
to pull all existing cpDefinitionOption and cpDefinitionOptionValue IDs from
given JSON structure.
Method is applicable to content of orderItem.json field.

Use method
`com.liferay.commerce.product.internal.util.CPInstanceHelperImpl#
getCPInstanceCPDefinitionOptionRelsMap(long)` to get CPDefinitionOption and
CPDefintionOptionValue Map, representing option configuration for a given
CPInstance.

#### Changes in Data Structure
**Before:**

```
[
  {
    "key":"material",
    "value":["metal"]
  }
]
```

**After:**

```
|--------------------------|-----------------------|--------------------------------------|
|CPInstanceOptionValueRel |
|--------------------------|-----------------------|--------------------------------------|
|cpInstanceOptionValueRelId|cpDefinitionOptionRelId|cpDefinitionOptionValueId|cpInstanceId|
|--------------------------|-----------------------|--------------------------------------|
| 1890044| 189002| 189004| 190001|
|--------------------------|-----------------------|--------------------------------------|
```

#### Why was this change made?

This change was introduced to implement Product Bundles and allow administrators
to provide products with advanced pricing scenarios.

---------------------------------------