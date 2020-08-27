UPDATE
  CommerceOrder
SET
  commercePaymentMethodKey = (
    SELECT
      engineKey
    FROM
      CommercePaymentMethod
    INNER JOIN
      CommerceOrder
    ON
      CommercePaymentMethod.commercePaymentMethodId = CommerceOrder.commercePaymentMethodId
  );