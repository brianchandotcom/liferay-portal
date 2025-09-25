import { Fragment } from "react";
import { Product } from "liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0";

function getKeys(product: Product) {
  try {
    const productSpecificationGroups = Object.groupBy(
      product.productSpecifications ?? [],
      (productSpecification) =>
        String(productSpecification.specificationGroupTitle ?? "")
    );

    return productSpecificationGroups;
  } catch {
    return {};
  }
}

export default function ProductSpecifications({
  product,
}: {
  product: Product;
}) {
  const productSpecificationGroups = getKeys(product);

  return (
    <div className="overflow-x-auto rounded-lg border border-gray-200">
      <table className="w-full text-sm text-left border-collapse">
        <tbody className="divide-y divide-gray-200">
          {Object.entries(productSpecificationGroups).map(
            ([key, productSpecifications], index) => (
              <Fragment key={index}>
                <tr className="bg-gray-50">
                  <td
                    colSpan={2}
                    className="px-4 py-2 font-semibold text-gray-500 uppercase tracking-wide"
                  >
                    {key}
                  </td>
                </tr>

                {productSpecifications?.map((spec, idx) => (
                  <tr key={idx}>
                    <td className="px-4 py-2 text-black font-bold" width={250}>
                      {spec.specificationTitle}
                    </td>
                    <td className="px-4 py-4">{spec.value}</td>
                  </tr>
                ))}
              </Fragment>
            )
          )}
        </tbody>
      </table>
    </div>
  );
}
