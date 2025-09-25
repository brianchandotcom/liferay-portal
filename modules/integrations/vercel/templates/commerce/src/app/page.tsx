import { Metadata } from "next";
import { PageProduct } from "liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0";

import { ProductCatalog } from "@/components/product/product-catalog";
import { getServerURL } from "@/lib/server";
import { getProductsPage } from "./data";
import { liferay } from "@/liferay/server";
import { ProductFilters } from "@/components/product/product-filters";

const defaultMetadata = {
  title: `${liferay.getChannel().siteName} | Products`,
  description:
    "Browse our product catalog using Liferay DXP as a Commerce Platform",
};

export async function generateMetadata(): Promise<Metadata> {
  const url = await getServerURL();

  return {
    ...defaultMetadata,
    metadataBase: new URL(url),
    openGraph: {
      ...defaultMetadata,
      locale: "en_US",
      siteName: liferay.getChannel().siteName,
      type: "website",
      url: url,
    },
  };
}

const getUniqueValue = <T,>(value: T | T[] | undefined, defaultValue: T) => {
  if (Array.isArray(value)) {
    return value[0];
  }

  return value || defaultValue;
};

const getArrayValue = <T,>(value: T | T[] | undefined, defaultValue: T[]) => {
  if (Array.isArray(value)) {
    return value;
  }

  if (!value) {
    return defaultValue;
  }

  return [value];
};

const getNormalizedSearchParams = async ({
  searchParams,
}: {
  searchParams: PageProps<"/">["searchParams"];
}) => {
  const params = await searchParams;

  const page = getUniqueValue(params.page, "1");
  const pageSize = getUniqueValue(params.pageSize, "15");
  const keywords = getUniqueValue(params.keywords, undefined);
  const viewMode = getUniqueValue(params.viewMode, "grid") as "grid" | "list";

  const specificationValues = getArrayValue(params.specificationValues, []);

  return { page, pageSize, keywords, specificationValues, viewMode };
};

export default async function ProductCatalogPage(props: PageProps<"/">) {
  const { keywords, page, pageSize, specificationValues, viewMode } =
    await getNormalizedSearchParams({ searchParams: props.searchParams });

  const { data, error } = await getProductsPage({
    keywords,
    liferay,
    page,
    pageSize,
    specificationValues,
  });

  if (error || !data) {
    console.error(error);

    return <h1>Error...</h1>;
  }

  return (
    <div className="flex flex-col md:flex-row gap-4">
      <aside>
        <ProductFilters />
      </aside>

      <ProductCatalog
        viewMode={viewMode}
        keywords={keywords || ""}
        pageProduct={data as Required<PageProduct>}
      />
    </div>
  );
}
