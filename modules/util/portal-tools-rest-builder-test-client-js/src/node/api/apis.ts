export * from './entityModelResourceTestEntity1Api';
import { EntityModelResourceTestEntity1Api } from './entityModelResourceTestEntity1Api';
export * from './entityModelResourceTestEntity2Api';
import { EntityModelResourceTestEntity2Api } from './entityModelResourceTestEntity2Api';
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

export const APIS = [EntityModelResourceTestEntity1Api, EntityModelResourceTestEntity2Api, TestEntityApi, TestEntityAddressApi];
