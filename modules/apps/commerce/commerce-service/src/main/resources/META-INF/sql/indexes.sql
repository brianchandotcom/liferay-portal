create unique index IX_17D56F1B on CPDAvailabilityEstimate (CProductId);
create index IX_E560850D on CPDAvailabilityEstimate (commerceAvailabilityEstimateId);
create index IX_609B2AF4 on CPDAvailabilityEstimate (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_2A31024F on CPDefinitionInventory (CPDefinitionId, ctCollectionId);
create index IX_E7573C34 on CPDefinitionInventory (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_9C196570 on CPDefinitionInventory (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_E7C1FC36 on CPDefinitionInventory (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create unique index IX_9DD3ABD3 on CommerceAddressRestriction (classNameId, classPK, countryId);
create index IX_AE21488 on CommerceAddressRestriction (countryId);

create index IX_72527224 on CommerceAvailabilityEstimate (companyId);
create index IX_EA65A078 on CommerceAvailabilityEstimate (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_12131FC1 on CommerceOrder (billingAddressId);
create index IX_7DD246EA on CommerceOrder (commerceAccountId, groupId, orderStatus);
create index IX_81097E4C on CommerceOrder (commerceAccountId, orderStatus);
create index IX_DFF1932E on CommerceOrder (companyId, commerceAccountId);
create index IX_48EEEDEE on CommerceOrder (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_9ACAF78A on CommerceOrder (createDate, commerceAccountId, orderStatus);
create index IX_4F4CAEE4 on CommerceOrder (groupId, commerceAccountId, orderStatus);
create index IX_9C04F6F8 on CommerceOrder (groupId, commercePaymentMethodKey[$COLUMN_LENGTH:75$]);
create index IX_67E0AF05 on CommerceOrder (groupId, userId, orderStatus);
create index IX_4B11FAD8 on CommerceOrder (shippingAddressId);
create index IX_75679B1F on CommerceOrder (userId, createDate, orderStatus);
create index IX_5AF685CD on CommerceOrder (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_58101B8F on CommerceOrder (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_2E1BB39D on CommerceOrderItem (CPInstanceId);
create index IX_F9E8D927 on CommerceOrderItem (CProductId);
create index IX_2D8339EE on CommerceOrderItem (bookedQuantityId);
create index IX_415AF3E3 on CommerceOrderItem (commerceOrderId, CPInstanceId);
create index IX_15B37023 on CommerceOrderItem (commerceOrderId, subscription);
create index IX_12257E21 on CommerceOrderItem (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_8E1472FB on CommerceOrderItem (parentCommerceOrderItemId);

create index IX_CEB86C22 on CommerceOrderNote (commerceOrderId, restricted);
create index IX_EF4EEF80 on CommerceOrderNote (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);

create index IX_CF274005 on CommerceOrderPayment (commerceOrderId);

create index IX_72C90BD4 on CommerceOrderType (companyId, active_);
create index IX_4EC1CAC8 on CommerceOrderType (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_CAB43113 on CommerceOrderType (displayDate, status);
create index IX_56A0F58A on CommerceOrderType (expirationDate, status);

create unique index IX_CBAD3B91 on CommerceOrderTypeRel (classNameId, classPK, commerceOrderTypeId);
create index IX_7813C75C on CommerceOrderTypeRel (classNameId, commerceOrderTypeId);
create index IX_AA661546 on CommerceOrderTypeRel (commerceOrderTypeId);
create index IX_22C116C7 on CommerceOrderTypeRel (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);

create index IX_F5105190 on CommerceShipment (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_616BDD15 on CommerceShipment (groupId, commerceAddressId);
create index IX_68FBA2B5 on CommerceShipment (groupId, status);

create index IX_3615B923 on CommerceShipmentItem (commerceOrderItemId);
create unique index IX_4FAC36D0 on CommerceShipmentItem (commerceShipmentId, commerceOrderItemId, commerceInventoryWarehouseId);
create index IX_41C840C3 on CommerceShipmentItem (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_DB0BB83C on CommerceShipmentItem (groupId);

create index IX_42E5F6EF on CommerceShippingMethod (groupId, active_);
create unique index IX_C4557F93 on CommerceShippingMethod (groupId, engineKey[$COLUMN_LENGTH:75$]);

create unique index IX_D7D137B1 on CommerceSubscriptionEntry (commerceOrderItemId);
create index IX_43E6F382 on CommerceSubscriptionEntry (companyId, userId);
create index IX_B99DE058 on CommerceSubscriptionEntry (groupId, companyId, userId);
create index IX_6D080A04 on CommerceSubscriptionEntry (groupId, userId);
create index IX_B496E103 on CommerceSubscriptionEntry (subscriptionStatus);
create index IX_4363DED4 on CommerceSubscriptionEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_943E0A56 on CommerceSubscriptionEntry (uuid_[$COLUMN_LENGTH:75$], groupId);