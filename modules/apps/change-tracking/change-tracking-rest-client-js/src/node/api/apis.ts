export * from './cTCollectionApi';
import { CTCollectionApi } from './cTCollectionApi';
export * from './cTEntryApi';
import { CTEntryApi } from './cTEntryApi';
export * from './cTProcessApi';
import { CTProcessApi } from './cTProcessApi';
export * from './cTRemoteApi';
import { CTRemoteApi } from './cTRemoteApi';
import * as http from 'http';

export class HttpError extends Error {
    constructor (public response: http.IncomingMessage, public body: any, public statusCode?: number) {
        super('HTTP request failed');
        this.name = 'HttpError';
    }
}

export { RequestFile } from '../model/models';

export const APIS = [CTCollectionApi, CTEntryApi, CTProcessApi, CTRemoteApi];
