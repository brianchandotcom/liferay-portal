import Promise from 'metal-promise';
import {mockDistribution} from 'test/data';

export const fetch = jest.fn(() => Promise.resolve([mockDistribution()]));
