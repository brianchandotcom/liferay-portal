"use client";

import { Product } from "liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0";
import { ChevronLeft, ChevronRight } from "lucide-react";
import { useState } from "react";
import clsx from "clsx";
import Image from "next/image";

import { Badge } from "@/components/ui/badge";
import { Card } from "@/components/ui/card";
import { getSkuDetails, handleImageError } from "@/lib/product";
import ProductQuickActions from "./product-quick-actions";
import ProductUOM from "./product-uom";
import ProductSpecifications from "./product-specifications";

const ProductDetail = ({ product }: { product: Product }) => {
  const [selectedImageIndex, setSelectedImageIndex] = useState(0);

  const images = product.images?.length
    ? product.images
    : [{ src: product.urlImage }];

  const productPrice = getSkuDetails(product);

  const nextImage = () => {
    setSelectedImageIndex((prev) => (prev + 1) % images.length);
  };

  const prevImage = () => {
    setSelectedImageIndex((prev) => (prev - 1 + images.length) % images.length);
  };

  return (
    <div>
      <div className="grid grid-cols-1 lg:grid-cols-5 gap-8">
        <div className="lg:col-span-2">
          <div className="space-y-4">
            <Card>
              <div className="relative">
                <Image
                  alt={product.name as string}
                  className="w-full aspect-square object-cover rounded-lg"
                  height={500}
                  quality={500}
                  src={images![selectedImageIndex].src as string}
                  unoptimized
                  width={480}
                  onError={handleImageError}
                />

                <button
                  onClick={prevImage}
                  className="absolute left-2 top-1/2 transform -translate-y-1/2 bg-white/80 rounded-full p-2 hover:bg-white"
                >
                  <ChevronLeft className="h-4 w-4" />
                </button>

                <button
                  onClick={nextImage}
                  className="absolute right-2 top-1/2 transform -translate-y-1/2 bg-white/80 rounded-full p-2 hover:bg-white"
                >
                  <ChevronRight className="h-4 w-4" />
                </button>
              </div>
            </Card>

            <div className="flex gap-2 overflow-x-auto">
              {images.map((image, index) => (
                <button
                  key={index}
                  onClick={() => setSelectedImageIndex(index)}
                  className={clsx(
                    "flex-shrink-0 w-16 h-16 rounded-md overflow-hidden border-2",
                    {
                      "border-primary": selectedImageIndex === index,
                      "border-transparent": selectedImageIndex !== index,
                    }
                  )}
                >
                  <Image
                    alt={image.title || ""}
                    className="w-full h-full object-cover"
                    height={16}
                    quality={100}
                    src={image.src as string}
                    unoptimized
                    width={16}
                    onError={handleImageError}
                  />
                </button>
              ))}
            </div>
          </div>
        </div>

        <div className="lg:col-span-2 space-y-6">
          <h1 className="text-2xl font-bold mb-2">{product.name}</h1>

          {!!productPrice.availability?.stockQuantity && (
            <div>
              {productPrice.availability?.stockQuantity <= 10 && (
                <Badge variant="destructive" className="mb-2 mr-2">
                  LOW STOCK
                </Badge>
              )}

              <span className="text-sm">
                Only {productPrice.availability?.stockQuantity} left in stock
              </span>
            </div>
          )}

          <div className="text-sm">
            Estimate Incoming Days:{" "}
            <b>{productPrice.availabilityEstimateName}</b>
          </div>

          <div>
            <p className="text-sm mb-1">SKU: {productPrice.sku}</p>
          </div>

          <div className="space-y-2">
            {productPrice.gtin && (
              <p className="text-sm">GTIN: {productPrice.gtin}</p>
            )}

            {productPrice.mfrPartNumber && (
              <p className="text-sm">MPN: {productPrice.mfrPartNumber}</p>
            )}
          </div>

          <div
            dangerouslySetInnerHTML={{ __html: product.description ?? "" }}
          />

          {!!productPrice.skuUnitOfMeasures?.length && (
            <ProductUOM uom={productPrice?.skuUnitOfMeasures} />
          )}
        </div>

        <div className="lg:col-span-1 space-y-6">
          <Card className="p-4 justify-end">
            {productPrice.discount && (
              <>
                <div className="text-right">
                  <div className="text-sm">List Price</div>
                  <div className="text-xl font-bold">
                    {productPrice.originalPrice}
                  </div>
                </div>

                <div className="text-right">
                  <div className="text-sm">Promo Price</div>
                  <div className="text-xl font-bold">
                    {productPrice.finalPrice}
                  </div>
                </div>

                {productPrice.discount && (
                  <div className="text-right">
                    <div className="text-sm">Discount</div>
                    <span className="text-sm text-red-600 text-discount">
                      {productPrice.discountPercent}%
                    </span>
                  </div>
                )}
              </>
            )}

            <div
              className={clsx("text-right", {
                "border-t": productPrice.discount,
              })}
            >
              <div className="text-sm">Final Price</div>
              <div className="text-2xl font-bold text-price">
                {productPrice.finalPrice}
              </div>
            </div>
          </Card>

          <ProductQuickActions />
        </div>
      </div>

      <div className="mt-12">
        <ProductSpecifications product={product} />
      </div>
    </div>
  );
};

export default ProductDetail;
