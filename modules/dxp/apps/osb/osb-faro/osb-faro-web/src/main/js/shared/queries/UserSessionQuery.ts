import {gql} from 'apollo-boost';
import {SessionEntityTypes} from 'shared/util/constants';

export interface UserSessionEvent {
	canonicalUrl: string;
	createDate: string;
	name: string;
	pageDescription: string;
	pageTitle: string;
	referrer: string;
	url: string;
}

export interface UserSession {
	browserName: string;
	completeDate: Date;
	contentLanguageID: string;
	description: string;
	devicePixelRatioz: number;
	deviceType: string;
	events: Event[];
	keywords: string;
	languageID: string;
	screenHeight: number;
	screenWidth: number;
	timezoneOffset: string;
	userAgent: string;
}

export interface UserSessionData {
	eventsByUserSessions: {
		totalEvents: number;
		userSessions: UserSession[];
	};
}

export interface UserSessionVariables {
	channelId: string;
	entityId: string;
	entityType: SessionEntityTypes;
	keywords?: string;
	page: number;
	rangeEnd?: string;
	rangeKey?: number;
	rangeStart?: string;
	size: number;
}

export default gql`
	query UserSession(
		$channelId: String!
		$entityId: String!
		$entityType: EntityType!
		$keywords: String
		$page: Int!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
	) {
		eventsByUserSessions(
			channelId: $channelId
			entityId: $entityId
			entityType: $entityType
			keywords: $keywords
			page: $page
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			size: $size
		) {
			userSessions {
				... on UserSession {
					browserName
					completeDate
					contentLanguageId
					createDate
					devicePixelRatio
					deviceType
					events {
						canonicalUrl
						createDate
						name
						pageDescription
						pageKeywords
						pageTitle
						referrer
						url
					}
					languageId
					screenHeight
					screenWidth
					timezoneOffset
					userAgent
				}
			}
			totalEvents
		}
	}
`;
