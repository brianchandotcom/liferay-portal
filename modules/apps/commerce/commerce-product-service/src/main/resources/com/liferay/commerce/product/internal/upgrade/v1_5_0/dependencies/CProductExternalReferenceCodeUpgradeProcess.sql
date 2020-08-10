UPDATE
  CProduct
SET
  CProduct.externalReferenceCode = (
    SELECT
      CPDefinition.externalReferenceCode
    FROM
      CPDefinition
    INNER JOIN
      CProduct
    ON
      CPDefinition.CProductId = CProduct.CProductId
  );