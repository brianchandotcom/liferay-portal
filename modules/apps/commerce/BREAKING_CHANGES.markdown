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

### Move Commerce administration from the Control Panel to the new 7.3 Global App menu
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

### Destination Names Changed
- **Date:** 2021-Feb-22
- **JIRA Ticket:** [COMMERCE-4762](https://issues.liferay.com/browse/COMMERCE-5788)

#### What changed?

Any methods that references `externalReferenceCode` has been rearranged.

Classes that have methods that were updated:

- `com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService`
- `com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService`
- `com.liferay.commerce.price.list.service.CommercePriceEntryLocalService`
- `com.liferay.commerce.price.list.service.CommercePriceEntryService`
- `com.liferay.commerce.price.list.service.CommercePriceListLocalService`
- `com.liferay.commerce.price.list.service.CommercePriceListService`
- `com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService`
- `com.liferay.commerce.price.list.service.CommerceTierPriceEntryService`
- `com.liferay.commerce.pricing.service.CommercePriceModifierLocalService`
- `com.liferay.commerce.pricing.service.CommercePriceModifierService`
- `com.liferay.commerce.pricing.service.CommercePricingClassLocalService`
- `com.liferay.commerce.pricing.service.CommercePricingClassService`
- `com.liferay.commerce.product.service.CommerceCatalogLocalService`
- `com.liferay.commerce.product.service.CommerceCatalogService`
- `com.liferay.commerce.product.service.CommerceChannelLocalService`
- `com.liferay.commerce.product.service.CommerceChannelService`
- `com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService`
- `com.liferay.commerce.product.service.CPAttachmentFileEntryService`
- `com.liferay.commerce.product.service.CPDefinitionLocalService`
- `com.liferay.commerce.product.service.CPDefinitionService`
- `com.liferay.commerce.product.service.CPInstanceLocalService`
- `com.liferay.commerce.product.service.CPInstanceService`
- `com.liferay.commerce.product.service.CPOptionLocalService`
- `com.liferay.commerce.product.service.CPOptionService`
- `com.liferay.commerce.product.service.CPOptionValueLocalService`
- `com.liferay.commerce.product.service.CPOptionValueService`
- `com.liferay.commerce.product.service.CProductLocalService`
- `com.liferay.commerce.product.service.CProductService`
- `com.liferay.commerce.service.CommerceAddressLocalService`
- `com.liferay.commerce.service.CommerceAddressService`
- `com.liferay.commerce.service.CommerceOrderItemLocalService`
- `com.liferay.commerce.service.CommerceOrderItemService`
- `com.liferay.commerce.service.CommerceOrderLocalService`
- `com.liferay.commerce.service.CommerceOrderNoteLocalService`
- `com.liferay.commerce.service.CommerceOrderNoteService`
- `com.liferay.commerce.service.CommerceOrderService`

#### Who is affected?

Anyone who references or uses methods from these classes.

#### How should I update my code?

Update methods to use the new corresponding method.

#### Why was this change made?

This change was introduced to follow Liferay source formatting.

---------------------------------------

### MiniCart Component Extensibility
- **Date:** 2021-Feb-12
- **JIRA Ticket:** [COMMERCE-4974](https://issues.liferay.com/browse/COMMERCE-4974)

#### What changed?
* The `MiniCart` Tag and FE React Component have been extended to support
partial and total replacement of its component views and labels and have
some of its features configurable, either via standard Tag attribute, or
via direct import of the JS implementation.
  * The exposed `MiniCartTag` attributes changed.
  * The `commerce-frontend-js` module is now exposed with a proper interface
  to allow a controlled access and exposure of its content (and in particular,
  of the `MiniCartContext` which is needed for `MiniCart` integration).
  * Usability and extensibility of the MiniCart component is documented
  [here](https://issues.liferay.com/browse/LRDOCS-9462).

#### Who is affected?

Developers relying or extending the the old `MiniCartTag`.

#### Why was this change made?

Alignment with Liferay DXP standards to support component extensibility.

---------------------------------------

### CommerceCountry and CommerceRegion Removed
- **Date:** 2021-Mar-02
- **JIRA Ticket:** [LPS-125991](https://issues.liferay.com/browse/LPS-125991)

#### What changed?

* The `CommerceCountry` and `CommerceRegion` tables have been removed from
the database.
* Service and persistence classes for `CommerceCountry` and `CommerceRegion`
have been removed.
* Rerences to `com.liferay.commerce.model.CommerceCountry` and
`com.liferay.commerce.model.CommerceRegion` have been replaced by
`com.liferay.portal.kernel.model.Country` and
`com.liferay.portal.kernel.model.Region`.
* Foreign keys that references `CommerceCountry` and `CommerceRegion` have
been renamed from `commerceCountryId` and `commerceRegionId` to `countryId` and
`regionId` respectively. Tables that have columns that were updated are:
  - `CommerceAddress`
  - `CommerceAddressRestriction`
  - `CommerceShippingFixedOptionRel`
  - `CommerceTaxFixedRateAddressRel`
* `com.liferay.commerce.country.CommerceCountryManager` is added for
retrieving commerce-specific countries. Available methods are:
  - `getBillingCountries`
  - `getBillingCountriesByChannelId`
  - `getShippingCountries`
  - `getShippingCountriesByChannelId`
  - `getWarehouseCountries`

#### Who is affected?

Anyone who references or uses these models and services.

#### How should I update my code?

Update any explicit reference to `CommerceCountry` and/or `CommerceRegion` with
the new corresponding models and services.

#### Why was this change made?

This change was introduced to remove duplicate models and services in Liferay
portal.

---------------------------------------

### Mini Compare Widget + Mini Compare Component
- **Date:** 2021-Mar-26
- **JIRA Ticket:** [COMMERCE-2909](https://issues.liferay.com/browse/COMMERCE-2909)

#### What changed?
* The `MiniCompare` FE implementation has been migrated from JSP/vanilla JS to
React (in `commerce-frontend-js`). It's loaded directly from the same JSP source
in `commerce-product-content-web`.
* CP Definition ID's are now stored in and eventually read from a cookie,
instead of using the session.
* The `CompareCheckboxTag` has been refactored to render via JSP. The JSP in
turn renders the `CompareCheckbox` React component.
    * The old Soy/MetalJS implementation of the `CompareCheckbox` in the
    `commerce-frontend-taglib` module has been **deprecated** and **removed**.

#### Who is affected?

Developers relying or extending the the old implementation of the
MiniCompare component/widget.

#### Why was this change made?

Due to Soy/MetalJS deprecation, alignment with Liferay DXP to support
Liferay Classic Theme in Commerce, paving the way for future DXP WEM
integration.

---------------------------------------

### Revamped Account Selector
- **Date:** 2021-Apr-27
- **JIRA Ticket:** [COMMERCE-5888](https://issues.liferay.com/browse/COMMERCE-6315)

#### What changed?
The `AccountSelectorTag` has been refactored to render via JSP and now
extends from the `IncludeTag`. The JSP
in turn hydrates and renders the `AccountSelector` React component,
which has been migrated from Soy/MetalJS.

During the runtime lifecycle, the communication with the server happens via
Commerce Headless API. Event names to notify other components about
account/order change have been renamed.

#### Who is affected?

Developers who rely or extend the old Soy/MetalJS component with
its related Tag.

#### Why was this change made?

Due to Soy/MetalJS deprecation, alignment with Liferay DXP to support
Liferay Classic Theme in Commerce, paving the way for future DXP WEM
integration.

---------------------------------------

### Product Card and Product Rendering Strategies in Storefront
- **Date:** 2021-Apr-27
- **JIRA Ticket:** [COMMERCE-5889](https://issues.liferay.com/browse/COMMERCE-5889)

#### What changed?

* The Product Card component is now ported to a JSP template to display
 product information. It is hydrated and rendered via
 CPContentListRenderer -> CPContentListEntryRenderer.
  * The Product Publisher, Search Results, Compare widgets now use these
  rendering strategies.
  * Commerce Theme Minium Site Initializer is now configured to use
  these rendering strategies.
  * The implementation now resides in the `commerce-product-content-web` module
  and is extensible through `CPContentRenderer` override, JSP override, or
  Liferay Dynamic Include.
  * The old Soy/MetalJS implementation of the Product Card in the
`commerce-frontend-taglib` module has been **deprecated** and **removed**.
  * The old Minium-specific `CPContentRenderer`'s in the
  `commerce-theme-minium-impl` module have been **deprecated** and **removed**.

* The `AddToCartTag` has been refactored to render via JSP  and now
extends from the `IncludeTag`. The JSP in turn
renders the `AddToCart` React component, including the `QuantitySelector`
React component.
  * The old Soy/MetalJS implementation of the `AddToCartButton` in the
`commerce-frontend-taglib` module has been **deprecated** and **removed**.
  * The old Soy/MetalJS implementation of the `QuantitySelector` in the
`commerce-frontend-taglib` module has been **deprecated** and **removed**.
  * The old `QuantitySelectorTag` in the `commerce-frontend-taglib` module
  has been **deprecated** and **removed**.

* The `PriceTag` has been refactored to render via JSP
 and now extends from the `IncludeTag`. The JSP both renders
the correctly structured template (for SEO purposes) and eventually
renders the `Price` React component (for user page landing).
  * The old Soy/MetalJS implementation of the `Price` in the
  `commerce-frontend-taglib` module has been **deprecated** and **removed**.

#### Who is affected?

Developers relying on or extending:
* the Minium-specific CP Content Renderers in `commerce-theme-minium-impl`
* the Soy/MetalJS `AddToCartTag` and `AddToCartButton*`.
* the Soy/MetalJS `QuantitySelectorTag` and `QuantitySelector*`.
* the Soy/MetalJS `PriceTag` and `Price*`.

#### Why was this change made?

Due to Soy/MetalJS deprecation, alignment with Liferay DXP to support
Liferay Classic Theme in Commerce, paving the way for future DXP WEM
integration.

---------------------------------------

### Standardize Method Names to Use AddOrUpdate vs. Upsert in *ServiceImpl Classes
- **Date:** 2021-Jun-4
- **JIRA Ticket:** [COMMERCE-6095](https://issues.liferay.com/browse/COMMERCE-6095)

#### What changed?

Any methods in commerce `*ServiceImpl` classes with name `upsert*` are now
renamed to `addOrUpdate*`.

#### Who is affected?

Developers who are using any `*ServiceImpl` `upsert*` methods will now need to
use the new corresponding methods `addOrUpdate*`.

#### Why was this change made?

This change was introduced to follow Liferay source formatting.

---------------------------------------

### BOM Feature Is Replaced by Shop by Diagram Feature
- **Date:** 2021-Oct-14
- **JIRA Ticket:** [COMMERCE-3030](https://issues.liferay.com/browse/COMMERCE-3030)

#### What changed?

BOM feature is replaced by Shop by Diagram feature. This means that
the enties Folder and BOM entry do not exist and there is a new
Product Type called Diagram.

#### Who is affected?

Product specialists that need to create diagrams for their store will
not need to create products with the Diagram product type.

#### Why was this change made?

This change was introduced to fix BOM issues and to better fullfill spare parts
use cases.

---------------------------------------

### Remove the Accounts widget so it is no longer a module in Liferay Commerce
- **Date:** 2021-Nov-21
- **JIRA Ticket:** [COMMERCE-7288](https://issues.liferay.com/browse/COMMERCE-7288)

#### What changed?

Commerce Account widget is replaced by Account widget.
All instances on a previously running system are updated.

#### Who is affected?

Developers who have customized the Commerce Account widget and/or used it with
site initializers.

End users (Account managers) that will use a different widget to manage accounts.

#### Why was this change made?

This change was introduced to be compliant with Commerce Account migration to
Account.

---------------------------------------