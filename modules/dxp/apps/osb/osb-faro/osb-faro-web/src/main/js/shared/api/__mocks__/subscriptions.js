import {mockSubscription} from 'test/data';

export const fetch = jest.fn(() => Promise.resolve(mockSubscription()));
