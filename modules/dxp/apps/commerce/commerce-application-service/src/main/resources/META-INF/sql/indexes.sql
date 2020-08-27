create index IX_EE3BF4E3 on CAModelCProductRel (CProductId);
create index IX_D7646A6B on CAModelCProductRel (commerceApplicationModelId);

create index IX_3BF424B0 on CommerceApplicationBrand (companyId);

create index IX_B852259B on CommerceApplicationModel (commerceApplicationBrandId);
create index IX_47286C4E on CommerceApplicationModel (companyId);