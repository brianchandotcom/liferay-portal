import sendRequest from 'shared/util/request';
import {pickBy} from 'lodash';

export function addDistributionTab({
	distributionTab,
	distributionTabId,
	groupId,
	scope,
	segmentId
}) {
	return sendRequest({
		data: pickBy({
			distributionCardTabPreferences: distributionTab,
			distributionTabId,
			individualSegmentId: segmentId,
			scope
		}),
		method: 'POST',
		path: `main/${groupId}/preferences/distribution_tabs`
	});
}

export function fetchDefaultChannelId({groupId, scope}) {
	return sendRequest({
		data: {scope},
		method: 'GET',
		path: `main/${groupId}/preferences/default_channel_id`
	});
}

export function fetchUpgradeModalSeen({groupId, scope}) {
	return sendRequest({
		data: {scope},
		method: 'GET',
		path: `main/${groupId}/preferences/upgrade_modal_seen`
	});
}

export function fetchDistributionTabs({groupId, scope, segmentId}) {
	return sendRequest({
		data: pickBy({individualSegmentId: segmentId, scope}),
		method: 'GET',
		path: `main/${groupId}/preferences/distribution_tabs`
	});
}

export function fetchEmailReport({groupId}) {
	return sendRequest({
		method: 'GET',
		path: `main/${groupId}/preferences/email_report`
	});
}

export function updateEmailReport({channelId, groupId, report}) {
	return sendRequest({
		data: pickBy({channelId, ...report}),
		method: 'POST',
		path: `main/${groupId}/preferences/email_report`
	});
}

export function removeDistributionTab({
	distributionTabId,
	groupId,
	scope,
	segmentId
}) {
	return sendRequest({
		data: pickBy({
			distributionTabId,
			individualSegmentId: segmentId,
			scope
		}),
		method: 'DELETE',
		path: `main/${groupId}/preferences/distribution_tabs`
	});
}

export function updateDefaultChannelId({defaultChannelId, groupId, scope}) {
	return sendRequest({
		data: {defaultChannelId, scope},
		method: 'POST',
		path: `main/${groupId}/preferences/default_channel_id`
	});
}

export function updateUpgradeModalSeen({groupId, scope, upgradeModalSeen}) {
	return sendRequest({
		data: {scope, upgradeModalSeen},
		method: 'POST',
		path: `main/${groupId}/preferences/upgrade_modal_seen`
	});
}
