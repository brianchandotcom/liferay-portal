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

### Concurrency Management for Inventory
- **Date:** 2020-Sep-3
- **JIRA Ticket:** [COMMERCE-4565](https://issues.liferay.com/browse/COMMERCE-4565)

#### What changed?

Moved Commerce to the same level as Applications and Control Panel in the
Applications Menu. Also dropped the commerce-admin-api and commerce-admin-web
modules.

#### Who is affected?

Developers: It's not possible anymore to add items to Commerce->Settings section
by implementing CommerceAdminModule.

End Users: The Commerce sections are now at the same level as the Control Panel.

#### Why was this change made?

This change was made in order not to lose the navigation scope in the header
bar while remaining compliant with DXP 7.3 standards.

---------------------------------------

### Files Moved
- **Date:** 2020-Aug-20
- **JIRA Ticket:** [COMMERCE-4052](https://issues.liferay.com/browse/COMMERCE-4052)

#### What changed?

Files listed below have moved:

- `com.liferay.commerce.pricing.web.servlet.taglib.ui.
CommerceDiscountScreenNavigationConstants;`
- `com.liferay.commerce.pricing.web.servlet.taglib.ui.
CommercePricingClassScreenNavigationConstants;`

#### Who is affected?

Anyone who references or uses these files.

#### How should I update my code?
Replace old references with the new package path.

New package paths:
- `com.liferay.commerce.pricing.web.internal.constants.
CommerceDiscountScreenNavigationConstants;`
- `com.liferay.commerce.pricing.web.internal.constants.
CommercePricingClassScreenNavigationConstants;`

#### Why was this change made?

This was moved to follow Liferay coding pattern.

---------------------------------------

### Destination Names Changed
- **Date:** 2020-Sep-10
- **JIRA Ticket:** [COMMERCE-4762](https://issues.liferay.com/browse/COMMERCE-4762)

#### What changed?

The prefix `commerce_` has been added to the Commerce destinations defined in
`com.liferay.commerce.constants.CommerceDestinationNames`:

- `liferay/commerce_order_status`;
- `liferay/commerce_payment_status`;
- `liferay/commerce_order_status`;
- `liferay/commerce_payment_status`;
- `liferay/commerce_subscription_status`.

#### Who is affected?

Anyone who references or uses these destinations.

#### How should I update my code?

Update any explicit reference to commerce destinations with the new names.

#### Why was this change made?

This change was introduced to follow the Liferay naming pattern.

---------------------------------------