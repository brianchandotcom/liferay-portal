import Promise from 'metal-promise';

export const addDistributionTab = jest.fn(() =>
	Promise.resolve({
		distributionCardTabPreferencesMap: {
			tab1: {context: 'demographics', id: 'tab1', title: 'Tab 1'},
			tab2: {context: 'demographics', id: 'tab2', title: 'Tab 2'}
		},
		order: ['tab1', 'tab2']
	})
);

export const removeDistributionTab = jest.fn(() =>
	Promise.resolve({distributionCardTabPreferencesMap: {}, order: []})
);

export const fetchDistributionTabs = jest.fn(() =>
	Promise.resolve({
		distributionCardTabPreferencesMap: {
			tab1: {context: 'demographics', id: 'tab1', title: 'Tab 1'}
		},
		order: ['tab1']
	})
);

export const fetchDefaultChannelId = jest.fn(() =>
	Promise.resolve({defaultChannelId: '123456'})
);

export const fetchUpgradeModalSeen = jest.fn(() => Promise.resolve(false));

export const updateDefaultChannelId = jest.fn(() =>
	Promise.resolve({defaultChannelId: '123456'})
);

export const updateUpgradeModalSeen = jest.fn(() => Promise.resolve(true));
