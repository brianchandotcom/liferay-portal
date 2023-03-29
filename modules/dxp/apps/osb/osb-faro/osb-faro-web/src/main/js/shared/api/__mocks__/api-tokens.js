import * as data from 'test/data';
import Promise from 'metal-promise';

export const generate = jest.fn(() => Promise.resolve(data.mockApiToken()));

export const search = jest.fn(() => Promise.resolve([data.mockApiToken()]));
