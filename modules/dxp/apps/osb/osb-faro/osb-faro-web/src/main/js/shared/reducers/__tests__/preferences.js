import reducer from '../preferences';
import {DistributionTab, RemoteData} from 'shared/util/records';
import {List, Map} from 'immutable';
import {actionTypes as preferencesActionTypes} from 'shared/actions/preferences';
import {PreferencesScopes} from 'shared/util/constants';

describe('Preferences Reducer', () => {
	it('should get default channel id', () => {
		const defaultChannelId = '123';

		const action = {
			meta: {scope: PreferencesScopes.User},
			payload: {defaultChannelId},
			type: preferencesActionTypes.FETCH_DEFAULT_CHANNEL_ID_SUCCESS
		};

		const state = reducer(new Map(), action);

		expect(
			state.getIn([PreferencesScopes.User, 'defaultChannelId', 'data'])
		).toEqual(defaultChannelId);
	});

	it('should update default channel id', () => {
		const defaultChannelId = '123';

		const prevDefaultChannelId = '321';

		const action = {
			meta: {scope: PreferencesScopes.User},
			payload: {defaultChannelId},
			type: preferencesActionTypes.UPDATE_DEFAULT_CHANNEL_ID_SUCCESS
		};

		const prevState = new Map({
			user: new Map({
				defaultChannelId: new RemoteData({
					data: prevDefaultChannelId,
					error: false,
					loading: false
				})
			})
		});

		expect(
			prevState.getIn([
				PreferencesScopes.User,
				'defaultChannelId',
				'data'
			])
		).toEqual(prevDefaultChannelId);

		const state = reducer(prevState, action);

		expect(
			state.getIn([PreferencesScopes.User, 'defaultChannelId', 'data'])
		).toEqual(defaultChannelId);
	});

	it('should update upgradeModalSeen', () => {
		const upgradeModalSeen = true;

		const prevUpgradeModalSeen = false;

		const action = {
			meta: {scope: PreferencesScopes.User},
			payload: upgradeModalSeen,
			type: preferencesActionTypes.UPDATE_UPGRADE_MODAL_SEEN_SUCCESS
		};

		const prevState = new Map({
			user: new Map({
				upgradeModalSeen: new RemoteData({
					data: prevUpgradeModalSeen,
					error: false,
					loading: false
				})
			})
		});

		expect(
			prevState.getIn([
				PreferencesScopes.User,
				'upgradeModalSeen',
				'data'
			])
		).toEqual(prevUpgradeModalSeen);

		const state = reducer(prevState, action);

		expect(
			state.getIn([PreferencesScopes.User, 'upgradeModalSeen', 'data'])
		).toEqual(upgradeModalSeen);
	});

	it('should get distribution tabs', () => {
		const id = '123';

		const distributionCardTabPreferencesMap = {
			test: {context: 'demographics', id: 'test', title: 'test'}
		};

		const order = ['test'];

		const action = {
			meta: {id, scope: PreferencesScopes.User},
			payload: {distributionCardTabPreferencesMap, order},
			type: preferencesActionTypes.FETCH_DISTRIBUTION_TABS_SUCCESS
		};

		const state = reducer(new Map(), action);

		expect(
			state.getIn([
				PreferencesScopes.User,
				'distributionCardTabs',
				id,
				'data',
				0
			])
		).toEqual(
			new DistributionTab({
				context: 'demographics',
				id: 'test',
				title: 'test'
			})
		);
	});

	it('should add a distribution tab', () => {
		const id = '123';

		const distributionCardTabPreferencesMap = {
			test: {context: 'demographics', id: 'test', title: 'test'}
		};

		const order = ['test'];

		const action = {
			meta: {id, scope: PreferencesScopes.User},
			payload: {distributionCardTabPreferencesMap, order},
			type: preferencesActionTypes.ADD_DISTRIBUTION_TABS_SUCCESS
		};

		const state = reducer(new Map(), action);

		expect(
			state.getIn([
				PreferencesScopes.User,
				'distributionCardTabs',
				id,
				'data',
				0
			])
		).toEqual(
			new DistributionTab({
				context: 'demographics',
				id: 'test',
				title: 'test'
			})
		);
	});

	it('should remove a distribution tab', () => {
		const id = '123';

		const action = {
			meta: {id, scope: PreferencesScopes.User},
			payload: {distributionCardTabPreferencesMap: {}, order: []},
			type: preferencesActionTypes.REMOVE_DISTRIBUTION_TABS_SUCCESS
		};

		const distributionTabsIList = new List([
			new DistributionTab({
				context: 'demographics',
				id: 'test',
				title: 'test'
			})
		]);

		const prevState = new Map({
			group: new Map({
				distributionCardTabs: new Map({
					individualsDashboard: new RemoteData({
						data: distributionTabsIList,
						error: false,
						loading: false
					})
				})
			})
		});

		const state = reducer(prevState, action);

		expect(
			state.getIn([
				PreferencesScopes.User,
				'distributionCardTabs',
				id,
				'data'
			]).size
		).toBe(0);
	});
});
