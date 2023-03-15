import {getDevicesMapper} from '../devices';

describe('Shared HOCs Mappers - Devices', () => {
	it('should map devices information', () => {
		const mapper = getDevicesMapper(
			result => result.form.submissionsMetric
		);

		const data = {
			data: {
				form: {
					submissionsMetric: {
						browser: [
							{
								value: 2248.0,
								valueKey: 'Chrome'
							},
							{
								value: 782.0,
								valueKey: 'Firefox'
							},
							{
								value: 137.0,
								valueKey: 'Chrome Mobile'
							},
							{
								value: 136.0,
								valueKey: 'Safari'
							},
							{
								value: 124.0,
								valueKey: 'Mobile Safari'
							},
							{
								value: 109.0,
								valueKey: 'Edge'
							},
							{
								value: 39.0,
								valueKey: 'Unknown'
							},
							{
								value: 20.0,
								valueKey: 'Samsung Browser'
							},
							{
								value: 17.0,
								valueKey: 'Opera Desktop'
							},
							{
								value: 10.0,
								valueKey: 'Chromium Project'
							},
							{
								value: 8.0,
								valueKey: 'Android'
							},
							{
								value: 7.0,
								valueKey: 'Unknown iOS App'
							},
							{
								value: 6.0,
								valueKey: 'Vivaldi'
							},
							{
								value: 5.0,
								valueKey: 'Opera Mini for iOS'
							},
							{
								value: 5.0,
								valueKey: 'Unknown Linux App'
							},
							{
								value: 4.0,
								valueKey: 'Opera Mobile'
							},
							{
								value: 3.0,
								valueKey: 'Yowser for Windows'
							},
							{
								value: 2.0,
								valueKey: 'Firefox for Mobile'
							},
							{
								value: 2.0,
								valueKey: 'Unknown Mac OS X App - WebKit Engine'
							},
							{
								value: 2.0,
								valueKey: 'Waterfox'
							},
							{
								value: 1.0,
								valueKey: 'Chrome for iOS'
							},
							{
								value: 1.0,
								valueKey: 'Edge Mobile'
							},
							{
								value: 1.0,
								valueKey: 'QQ Browser'
							},
							{
								value: 1.0,
								valueKey: 'UBrowser'
							},
							{
								value: 1.0,
								valueKey: 'Yowser for Linux'
							}
						],
						device: [
							{
								metrics: [
									{
										value: 2065.0,
										valueKey: 'Windows'
									},
									{
										value: 995.0,
										valueKey: 'macOS'
									},
									{
										value: 136.0,
										valueKey: 'Linux'
									},
									{
										value: 107.0,
										valueKey: 'Mac OS X'
									},
									{
										value: 53.0,
										valueKey: 'Ubuntu'
									},
									{
										value: 3.0,
										valueKey: 'ChromeOS'
									},
									{
										value: 2.0,
										valueKey: 'Fedora'
									},
									{
										value: 1.0,
										valueKey: 'Unknown'
									}
								],
								value: 3362.0,
								valueKey: 'Desktop'
							},
							{
								metrics: [
									{
										value: 151.0,
										valueKey: 'Android'
									},
									{
										value: 61.0,
										valueKey: 'iOS'
									},
									{
										value: 9.0,
										valueKey: 'Unknown'
									},
									{
										value: 8.0,
										valueKey: 'FreeBSD'
									},
									{
										value: 1.0,
										valueKey: 'Windows Phone'
									}
								],
								value: 230.0,
								valueKey: 'SmartPhone'
							},
							{
								metrics: [
									{
										value: 67.0,
										valueKey: 'iOS'
									},
									{
										value: 12.0,
										valueKey: 'Android'
									}
								],
								value: 79.0,
								valueKey: 'Tablet'
							}
						]
					}
				}
			}
		};

		expect(mapper.props(data)).toMatchSnapshot();
	});

	it('should map devices information in correct order', () => {
		const mapper = getDevicesMapper(
			result => result.form.submissionsMetric
		);

		const data = {
			data: {
				form: {
					submissionsMetric: {
						browser: [
							{
								value: 9.0,
								valueKey: 'Chrome'
							}
						],
						device: [
							{
								metrics: [
									{
										value: 2.0,
										valueKey: 'Windows'
									},
									{
										value: 1.0,
										valueKey: 'macOS'
									}
								],
								value: 3.0,
								valueKey: 'Desktop'
							},
							{
								metrics: [
									{
										value: 4.0,
										valueKey: 'Android'
									}
								],
								value: 4.0,
								valueKey: 'SmartPhone'
							},
							{
								metrics: [
									{
										value: 1.0,
										valueKey: 'Tizen'
									}
								],
								value: 1.0,
								valueKey: 'Tv'
							},
							{
								metrics: [
									{
										value: 1.0,
										valueKey: 'Linux'
									}
								],
								value: 1.0,
								valueKey: 'SmallScreen'
							}
						]
					}
				}
			}
		};

		expect(mapper.props(data)).toMatchSnapshot();
	});
});
