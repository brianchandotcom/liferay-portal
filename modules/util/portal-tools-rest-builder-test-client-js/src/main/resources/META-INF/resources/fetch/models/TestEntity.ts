/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { NestedTestEntity } from './NestedTestEntity';
/**
 * https://www.schema.org/Document
 */
export type TestEntity = {
    dateCreated?: string;
    dateModified?: string;
    description?: string;
    documentId?: number;
    readonly id?: number;
    jsonProperty?: string;
    name?: string;
    nestedTestEntity?: NestedTestEntity;
    self?: string;
    testEntities?: TestEntity;
    type?: 'ChildTestEntity1' | 'ChildTestEntity2' | 'ChildTestEntity3';
};

