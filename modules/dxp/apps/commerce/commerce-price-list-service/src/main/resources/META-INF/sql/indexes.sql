create unique index IX_85F53E15 on CPLUserSegmentEntryRel (commercePriceListId, commerceUserSegmentEntryId);
create index IX_A215DC0A on CPLUserSegmentEntryRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1A99DD0C on CPLUserSegmentEntryRel (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_2083879C on CommercePriceEntry (CPInstanceId, commercePriceListId);
create index IX_CA7A2D0D on CommercePriceEntry (commercePriceListId);
create index IX_5E36B51E on CommercePriceEntry (companyId);
create index IX_E185EB20 on CommercePriceEntry (groupId);
create index IX_1578F03E on CommercePriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F45C6E40 on CommercePriceEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_473B4D8D on CommercePriceList (commerceCurrencyId);
create index IX_2AA1AF56 on CommercePriceList (companyId);
create index IX_31913054 on CommercePriceList (displayDate, status);
create index IX_2C5B7A3E on CommercePriceList (groupId, status);
create index IX_863045BB on CommercePriceList (parentCommercePriceListId);
create index IX_FCE28706 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_554D1708 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_A622C8AE on CommerceTierPriceEntry (commercePriceEntryId, minQuantity);
create index IX_F5D5725C on CommerceTierPriceEntry (companyId);
create index IX_D78EDFDE on CommerceTierPriceEntry (groupId);
create index IX_B6C47140 on CommerceTierPriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5D3847C2 on CommerceTierPriceEntry (uuid_[$COLUMN_LENGTH:75$], groupId);