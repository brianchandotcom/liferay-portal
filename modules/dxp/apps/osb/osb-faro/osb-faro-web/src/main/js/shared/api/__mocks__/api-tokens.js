import * as data from 'test/data';

export const generate = jest.fn(() => Promise.resolve(data.mockApiToken()));

export const search = jest.fn(() => Promise.resolve([data.mockApiToken()]));
