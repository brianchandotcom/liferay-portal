'use strict';

import navigate from '../../../src/main/resources/META-INF/resources/liferay/util/navigate.es';

describe(
    'Liferay.Util.navigate',
    () => {
        const sampleUrl = 'http://sampleurl.com';

        beforeEach(
            () => {
                Liferay = {};
            }
        );

        describe(
            'when SPA is enabled',
            () => {
                beforeEach(
                    () => {
                        Liferay.once = jest.fn();

                        Liferay.SPA = {
                            app: {
                                navigate: jest.fn()
                            }
                        };
                    }
                );

                it(
                    'should navigate to the given url using the provided Liferay.SPA.app.navigate helper',
                    () => {
                        navigate(sampleUrl);

                        expect(Liferay.SPA.app.navigate).toBeCalledWith(sampleUrl);
                    }
                );

                it(
                    'should setup one-time-only global listeners in the Liferay object if specified',
                    () => {
                        const listenerFn = jest.fn();

                        navigate(
                            sampleUrl,
                            {
                                'event1': listenerFn,
                                'event2': listenerFn
                            }
                        );

                        expect(Liferay.once).toHaveBeenCalledTimes(2);
                        expect(Liferay.once).toHaveBeenNthCalledWith(1, 'event1', listenerFn);
                        expect(Liferay.once).toHaveBeenNthCalledWith(2, 'event2', listenerFn);
                    }
                );
            }
        );

        describe(
            'when SPA is disabled',
            () => {
                it(
                    'should navigate to the given url using window.location.assign',
                    () => {
                        const spy = jest.spyOn(
                            console,
                            'error'
                        ).mockImplementation(
                            () => undefined
                        );

                        navigate(sampleUrl);

                        // JSDOM does not allow to mock location.href. Thus, we verify
                        // it is called by matching the error they log when an attempt
                        // at setting location.href is detected

                        expect(spy).toHaveBeenCalled();
                        expect(spy.mock.calls[0][0]).toMatch('Error: Not implemented: navigation (except hash changes)');

                        spy.mockRestore();
                    }
                );
            }
        );
    }
);