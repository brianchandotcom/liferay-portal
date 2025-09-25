import { SkuUnitOfMeasure } from "liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0";

import { Card } from "../ui/card";

export default function ProductUOM({ uom }: { uom: SkuUnitOfMeasure[] }) {
  return (
    <Card className="p-4">
      <h3 className="font-semibold mb-3">UOM</h3>
      <div className="space-y-2">
        <div className="grid grid-cols-4 gap-4 text-sm font-medium">
          <span>Unit</span>
          <span>Key</span>
          <span>Quantity</span>
          <span>Net Price</span>
        </div>

        {uom.map((item, index) => (
          <div
            key={index}
            className="grid grid-cols-4 gap-4 text-sm border-b border-border pb-2"
          >
            <span>{item.name}</span>
            <span>{item.key}</span>
            <span>{item.incrementalOrderQuantity}</span>
            <span className="font-bold text-slate-900">
              {item.price?.priceFormatted}
            </span>
          </div>
        ))}
      </div>
    </Card>
  );
}
