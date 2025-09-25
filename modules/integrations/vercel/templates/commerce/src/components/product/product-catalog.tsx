"use client";

import { PageProduct } from "liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0";
import { Grid, List, Search } from "lucide-react";
import { Button } from "@/components/ui/button";
import { ProductCard } from "@/components/product/product-card";
import { useRouter, useSearchParams } from "next/navigation";
import { PaginationBar } from "../pagination-bar";
import EmptyState from "../empty-state";
import { Input } from "../ui/input";

type Props = {
  keywords: string;
  pageProduct: Required<PageProduct>;
  viewMode: "grid" | "list";
};

export const ProductCatalog = ({ keywords, pageProduct, viewMode }: Props) => {
  const { totalCount, items: products } = pageProduct;

  const searchParams = useSearchParams();
  const router = useRouter();

  const setViewMode = (mode: Props["viewMode"]) => {
    const url = new URL(location.href);
    url.searchParams.set("viewMode", mode);
    router.push(url.href);
  };

  const specificationValues = searchParams.getAll("specificationValues");

  if ((keywords || specificationValues.length) && totalCount === 0) {
    return (
      <main className="flex-1">
        <EmptyState />
      </main>
    );
  }

  return (
    <main className="flex-1">
      <div className="relative flex-1 max-w-m mb-4">
        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 bg-white" />

        <Input
          placeholder="Search products..."
          defaultValue={keywords}
          className="pl-10 bg-white"
          onKeyDown={(event) => {
            if (event.key !== "Enter") {
              return;
            }

            const _searchParams = new URLSearchParams(searchParams);

            _searchParams.set("keywords", event.currentTarget.value);

            router.push(`/?${_searchParams.toString()}`);
          }}
        />
      </div>

      <div className="flex items-center justify-between mb-6">
        <div className="text-sm">
          {totalCount} Products Available{" "}
          {keywords && (
            <span>
              for <b>{keywords}</b>
            </span>
          )}
        </div>

        <div className="flex items-center rounded-md">
          <Button
            variant={viewMode === "grid" ? "default" : "outline"}
            size="sm"
            onClick={() => setViewMode("grid")}
            className="rounded-r-none"
          >
            <Grid className="h-4 w-4" />
          </Button>

          <Button
            variant={viewMode === "list" ? "default" : "outline"}
            size="sm"
            onClick={() => setViewMode("list")}
            className="rounded-l-none"
          >
            <List className="h-4 w-4" />
          </Button>
        </div>
      </div>

      <div
        className={`${
          viewMode === "list"
            ? "space-y-4"
            : "grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4"
        }`}
      >
        {products.map((product) => (
          <ProductCard key={product.id} product={product} viewMode={viewMode} />
        ))}
      </div>

      <PaginationBar
        currentPage={pageProduct.page}
        onPageChange={(page) =>
          router.push(
            `/?${new URLSearchParams({
              page: String(page),
              pageSize: String(pageProduct.pageSize),
              keywords: keywords ?? "",
            })}`
          )
        }
        onPageSizeChange={(pageSize) => {
          router.push(
            `/?${new URLSearchParams({
              page: String(pageProduct.page),
              pageSize: String(pageSize),
              keywords: keywords ?? "",
            })}`
          );
        }}
        pageSize={pageProduct.pageSize}
        totalCount={pageProduct.totalCount}
      />
    </main>
  );
};
