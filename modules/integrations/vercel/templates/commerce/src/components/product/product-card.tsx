import { Product } from "liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0";

import ProductListView from "./product-list-view";
import ProductGridView from "./product-grid-view";

interface ProductCardProps {
  product: Product;
  viewMode?: string;
}

export const ProductCard = ({
  product,
  viewMode = "grid",
}: ProductCardProps) => {
  if (viewMode === "list") {
    return <ProductListView product={product} />;
  }

  return <ProductGridView product={product} />;
};
