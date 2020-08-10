import * as Utils from 'utils/utils.es';

describe('Utility functions', () => {
    describe(':: isArray', () => {
        it('returns \'true\' if input value is an array', () => {
            expect(Utils.isArray([1, 2, 3, 'a'])).toBe(true);
        });

        it('returns \'false\' if input value is not an array', () => {
            expect(Utils.isArray('a')).toBe(false);
        });
    });

    describe(':: isNumber', () => {
        it('returns \'true\' if input value is a number', () => {
            expect(Utils.isNumber(1)).toBe(true);
            expect(Utils.isNumber(NaN)).toBe(true);
        });

        it('returns \'false\' if input value is not a number', () => {
            expect(Utils.isNumber('a')).toBe(false);
        });
    });

    describe(':: getColorHue', () => {
        it('returns a random HSL hue value', () => {
            const samples = 1000;

            for (let i = 0; i < samples; i++) {
                const value = Utils.getColorHue(),
                    isInRange = value >= 0 && value < 360;

                expect(isInRange).toBe(true);
            }
        });

        it('returns an appropriate different random HSL hue value if similar to the previous one', () => {
            global.Math.random = () => 0.4;

            expect(Utils.getColorHue(142)).toEqual(149);
        });
    });

    describe(':: setupDataset', () => {
        afterEach(() => {
            jest.clearAllMocks();
        });

        it('returns the correct sanitized dataset of an organization', () => {
            const inputPayload = {
                organizations: [
                    {name: 'org1', organizations: []},
                    {name: 'org2', organizations: []}
                ]
            };

            const expectedHSLPattern = /hsl\([-+]?[0-9]*\.?[0-9]+,75%,75%\)/;
            const outputPayload = {
                organizations: [
                    {name: 'org1', colorIdentifier: expect.stringMatching(expectedHSLPattern)},
                    {name: 'org2', colorIdentifier: expect.stringMatching(expectedHSLPattern)}
                ]
            };

            const result = Utils.setupDataset(inputPayload);

            expect(result).toMatchObject(outputPayload);
        });
    });

    describe(':: endpointBuilder', () => {
        let parameters;

        beforeEach(() => {
            parameters = {
                baseURL: 'http://someDomain.com',
                id: '1',
                path: 'users',
                queryParams: {pageStart: 1, pageSize: 10, q: 'query'}
            };
        });

        it('throws an error if no parameters are provided at all', () => {
            delete parameters.baseURL;
            delete parameters.id;
            delete parameters.path;
            delete parameters.queryParams;

            function endpointBuilder() {
                Utils.endpointBuilder(parameters);
            }

            expect(endpointBuilder).toThrowError(new Error('No API baseURL provided.'));
        });

        describe('returns the correct API endpoint based on the input parameters', () => {
            it('all parameters are provided', () => {
                expect(Utils.endpointBuilder(parameters))
                    .toBe('http://someDomain.com/organizations' +
                        '/1/users?pageStart=1&pageSize=10&q=query');
            });

            it('no parameters but baseURL are provided (defaults)', () => {
                delete parameters.id;
                delete parameters.path;
                delete parameters.queryParams;

                expect(Utils.endpointBuilder(parameters))
                    .toBe('http://someDomain.com/organizations/0/');
            });
        });
    });
});
