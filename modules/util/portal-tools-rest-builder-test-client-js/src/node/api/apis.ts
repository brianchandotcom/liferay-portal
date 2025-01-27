export * from './companyTestEntityApi';
import { CompanyTestEntityApi } from './companyTestEntityApi';
export * from './entityModelResourceTestEntity1Api';
import { EntityModelResourceTestEntity1Api } from './entityModelResourceTestEntity1Api';
export * from './entityModelResourceTestEntity2Api';
import { EntityModelResourceTestEntity2Api } from './entityModelResourceTestEntity2Api';
export * from './siteTestEntityApi';
import { SiteTestEntityApi } from './siteTestEntityApi';
export * from './testEntityApi';
import { TestEntityApi } from './testEntityApi';
export * from './testEntityAddressApi';
import { TestEntityAddressApi } from './testEntityAddressApi';
import * as http from 'http';

export class HttpError extends Error {
    constructor (public response: http.IncomingMessage, public body: any, public statusCode?: number) {
        super('HTTP request failed');
        this.name = 'HttpError';
    }
}

export { RequestFile } from '../model/models';

export const APIS = [CompanyTestEntityApi, EntityModelResourceTestEntity1Api, EntityModelResourceTestEntity2Api, SiteTestEntityApi, TestEntityApi, TestEntityAddressApi];
