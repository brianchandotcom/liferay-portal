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

*This document has been reviewed through commit `0a185c9`.*

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

### Changed How to Get Booked Quantities
- **Date:** 2020-Jan-28
- **JIRA Ticket:** [Commerce-2866](https://issues.liferay.com/browse/COMMERCE-2866)

#### What changed?

The method `getCommerceBookedQuantity` inside the class
`CommerceBookedQuantityLocalService` now requires two parameters instead of one.

Old: `getCommerceBookedQuantity(String sku)`
New: `getCommerceBookedQuantity(long companyId, String sku)`

#### Who is affected?

End Users: Booked quantity inventory counts may change.

Developers: Inventory services usage will change.

#### How should I update my code?

Internal parent module usage:

Every call to the method `getCommerceBookedQuantity(String sku)` should be
replaced by the new method `getCommerceBookedQuantity(long companyId,
String sku)`.

#### Why was this change made?

The breaking changes have been made, instead of following the deprecation
process, because the old behavior was just wrong. When getting the inventory's
booked quantity, the `CompanyId` was not being considered.

The result may not have been valid in the case of multi-tenant systems. In this
specific scenario, data was not separated between one instance and the others.

---------------------------------------

### Order Engine Refactor
- **Date:** 2020-Feb-6
- **JIRA Ticket:** [COMMERCE-2687](https://issues.liferay.com/browse/COMMERCE-2687)

#### What changed?

Operations that modify an Order's status should be routed through the Order
Engine now instead of using the order services. This includes submitting,
checking out, and approving orders.

Affected entities:
- `CommerceOrderService`
  - Method approveCommerceOrder() has been removed and is handled by the
  existing method in the service executeWorkflowTransition()
  - Method checkoutCommerceOrder() has been moved to the Order Engine
  - Method setCommerceOrderToTransmit() has been removed and replaced by
  transitioning an order to "In Progress"
  - Method submitCommerceOrder() has been removed and is handled by the
  checkout method in the Order Engine

#### Who is affected?

Developers: Have to change the scope of custom code.

#### How should I update my code?

Any custom code that calls the aforementioned methods should be transitioned
to use the Order Engine. Any additional code you may have that modifies an
order's status directly or applies logic to modify an order's status should
instead be implemented as a new CommerceOrderStatus.

#### Why was this change made?

This change has been made so that the Order flow can be more testable and
customisable, which can be achieved through custom implementations of the
Order Engine interface or through the addition of custom order statuses.

---------------------------------------

### CPInstance Entity Field json Is Replaced With New Entity CPInstanceOptionValueRel
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

### Channels Refactor
- **Date:** 2020-Mar-12
- **JIRA Ticket:** [COMMERCE-2702](https://issues.liferay.com/browse/COMMERCE-2702)

#### What changed?

Payments, shipments, taxes, notifications, health checks, and commerce
site-related configurations (site type, buyer order approval workflow, and
seller order acceptance workflow) are now scoped to channels instead of sites.

Affected entities:
- `CommercePaymentMethodGroupRel`
- `CommerceShippingMethod`
- `CommerceNotificationTemplate`
- `CommerceTaxMethod`

CommerceAdminConstants class from com.liferay.commerce.admin.constants package
has been removed.

Method getType() has been removed from CommerceAdminModule interface.

#### Who is affected?

Developers: Have to change the scope of custom code.

#### How should I update my code?

Any custom code has to be reviewed and aligned to the new scope. Changing scope
means that those items are associated with their channel groupId instead of the
site groupId.

#### Why was this change made?

This change has been made in order to have a flexible system where everything is
associated with a channel so that there are no limitations related to sites.
This is because a channel can be associated with a Liferay site or as an
external integration. This change will help keep all configurations consistent
across the whole platform.

---------------------------------------

### Concurrency Management for Inventory
- **Date:** 2020-Mar-24
- **JIRA Ticket:** [COMMERCE-2780](https://issues.liferay.com/browse/COMMERCE-2780)

#### What changed?

Any update action affecting:
- `CommerceInventoryBookedQuantity`
- `CommerceInventoryReplenishmentItem`
- `CommerceInventoryWarehouse`
- `CommerceInventoryWarehouseItem`

now requires a new parameter `mvccVersion`, used for concurrency management.

#### Who is affected?

Developers: Inventory services usage will change.

#### How should I update my code?

Every call to methods that update the previous mentioned entities require a
parameter `mvccVersion` used for concurrency management. The value to put in
this parameter can be found in the entity itself.

For example commerceInventoryWarehouse.getMvccVersion()

#### Why was this change made?

This change has been made in order to manage situations where a user tries to
update an entity that has already been updated by another user while the first
one was setting their changes.

**Example:**
- Given: An entity `commerceWarehouse` exists with an `mvccVersion` attribute
  set to `1` and the `name` set to `WarehouseName`.
- Two users access the `commerceWarehouse` entity.
- The first one changes the entity with parameters:
  - `name = "WarehouseNameChanged"`
  - `mvccVersion = 1`

- The system will update the `name` and automatically increase the `mvccVersion`
  to `2`.
- The second user then tries to change the entity with:
  - `name = "WarehouseWithoutName"`
  - `mvccVersion = 1`

  This time the system will refuse to do the update because the second user is
  trying to update an entity version that is no longer valid/available. This is
  because the actual `mvccVersion` is `2` and the user is trying to update the
  entity with `mvccVersion` is `1`.

---------------------------------------

### Product Sorting
- **Date:** 2020-May-3
- **JIRA Ticket:** [COMMERCE-3558](https://issues.liferay.com/browse/COMMERCE-3558)

#### What changed?

The product sorting dropdown has been moved from the Commerce "Search Results"
widget into its own widget named "Sort".

#### Who is affected?

Anyone who had upgraded to version 2.0.7 and has the Commerce "Search Results"
widget deployed on any page.

#### How should I update my code?

No code updates are needed, however the "Sort" widget will need to be added
to your existing catalog page or wherever you're using a "Search Result" widget
and want users to be able to sort products.

#### Why was this change made?

---------------------------------------

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

The prefix commerce_ has been added to the commerce destinations defined in
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

This change was introduced to follow Liferay naming pattern.

---------------------------------------