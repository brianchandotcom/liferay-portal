UPDATE
  CommerceOrderPayment
SET
  commercePaymentMethodKey = (
    SELECT
      engineKey
    FROM
      CommercePaymentMethod
    INNER JOIN
      CommerceOrderPayment
    ON
      CommercePaymentMethod.commercePaymentMethodId = CommerceOrderPayment.commercePaymentMethodId
  );