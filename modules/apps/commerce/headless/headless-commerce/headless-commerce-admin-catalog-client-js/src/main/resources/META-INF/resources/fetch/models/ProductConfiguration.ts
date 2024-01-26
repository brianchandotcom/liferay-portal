/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type ProductConfiguration = {
    allowBackOrder?: boolean;
    allowedOrderQuantities?: Array<number>;
    availabilityEstimateId?: number;
    availabilityEstimateName?: Record<string, string>;
    displayAvailability?: boolean;
    displayStockQuantity?: boolean;
    /**
     * The inventory engine that will be used to manage the product inventory
     */
    inventoryEngine?: string;
    /**
     * The low stock action that will be performed when a product is out of stock
     */
    lowStockAction?: string;
    maxOrderQuantity?: number;
    minOrderQuantity?: number;
    minStockQuantity?: number;
    multipleOrderQuantity?: number;
};

