/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Diagram } from '../models/Diagram';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DiagramService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Updates a diagram by its ID.
     * @returns Diagram Updated
     * @throws ApiError
     */
    public patchDiagram({
        diagramId,
        requestBody,
    }: {
        diagramId: number,
        requestBody: Diagram,
    }): CancelablePromise<Diagram> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/diagrams/{diagramId}',
            path: {
                'diagramId': diagramId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Gets the diagram by product external reference code.
     * @returns Diagram Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeDiagram({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Diagram> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/diagrams',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a diagram by product external reference code.
     * @returns Diagram Updated
     * @throws ApiError
     */
    public postProductByExternalReferenceCodeDiagram({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Diagram,
    }): CancelablePromise<Diagram> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/diagrams',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Gets the diagram by product ID.
     * @returns Diagram Successful operation
     * @throws ApiError
     */
    public getProductIdDiagram({
        id,
    }: {
        id: number,
    }): CancelablePromise<Diagram> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/diagrams',
            path: {
                'id': id,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a diagram by product ID.
     * @returns Diagram Updated
     * @throws ApiError
     */
    public postProductIdDiagram({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: Diagram,
    }): CancelablePromise<Diagram> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/diagrams',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
