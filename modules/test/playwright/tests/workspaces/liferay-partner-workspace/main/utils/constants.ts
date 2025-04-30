/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum EAccountRoles {
	PARTNER_MANAGER = '[Account] Partner Manager (PM)',
}

export enum EMDFClaimStatuses {
	APPROVED = 'Approved',
	DRAFT = 'Draft',
	IN_DIRECTOR_REVIEW = 'In Director Review',
	IN_FINANCE_REVIEW = 'In Finance Review',
	MORE_INFO_REQUESTED = 'More Info Requested',
	PENDING_MARKETING_REVIEW = 'Pending Marketing Review',
}

export enum EMDFRequestLiferayBusinessSalesGoals {
	LEAD_GENERATION = 'leadGeneration',
	NURTURE_EXISTING_PROSPECTS = 'nurtureExistingProspects',
	OTHER = 'other',
	THOUGHT_LEADERSHIP = 'thoughtLeadership',
}

export enum EMDFRequestStatuses {
	APPROVED = 'Approved',
	DRAFT = 'Draft',
	MARKETING_DIRECTOR_REVIEW = 'Marketing Director Review',
	MORE_INFO_REQUESTED = 'More Info Requested',
	PENDING_MARKETING_REVIEW = 'Pending Marketing Review',
}

export enum EMDFRequestTargetAudienceRoles {
	ADMINISTRATOR = 'administrator',
	ASSOCIATE_ANALYST = 'associateAnalyst',
	C_LEVEL_EXECUTIVE_VP = 'cLevelExecutiveVP',
	DEVELOPER_ENGINEER = 'developerEngineer',
	DIRECTOR_MANAGER = 'directorManager',
	ECOMMERCE_LEADERSHIP = 'ecommerceLeadership',
	INDEPENDENT_CONTRACTOR = 'independentContractor',
	PROJECT_MANAGER = 'projectManager',
}

export enum EMDFRequestAdditionalOptions {
	DXP_MIGRATION_UPGRADE = 'dxpMigrationUpgrade',
	MIGRATION_FROM_COMPETITOR_PLATFORM = 'migrationFromCompetitorPlatform',
}

export enum EMDFRequestTargetMarkets {
	AEROSPACE_DEFENSE = 'aerospaceDefense',
	AGRICULTURE = 'agriculture',
	AUTOMOTIVE = 'automotive',
	CONSTRUCTION_ENGINEERING = 'constructionEngineering',
	CONSULTING_MARKET_RESEARCH = 'consultingMarketResearch',
	EDUCATION = 'education',
	ENERGY = 'energy',
	FINANCIAL_SERVICES = 'financialServices',
	FOOD_SERVICES = 'foodServices',
	GOVERNMENT_FEDERAL = 'governmentFederal',
	GOVERNMENT_STATE_LOCAL = 'governmentStateLocal',
	HEALTHCARE = 'healthcare',
	HOSPITALITY_LEISURE = 'hospitalityLeisure',
	INSURANCE = 'insurance',
	MANUFACTURING = 'manufacturing',
	MEDIA_ENTERTAINMENT = 'mediaEntertainment',
	NOT_FOR_PROFIT_NGO = 'notForProfitNGO',
	PHARMACEUTICALS = 'pharmaceuticals',
	PROFESSIONAL_SERVICES_AGENCY_BUSINESS = 'professionalServicesAgencyBusiness',
	PROFESSIONAL_SERVICES_TECHNICAL_WEB_IT = 'professionalServicesTechnicalWebIT',
	RETAIL_CONSUMER_PRODUCTS = 'retailConsumerProducts',
	TECHNOLOGY = 'technology',
	TELECOMMUNICATIONS = 'telecommunications',
	TRANSPORTATION = 'transportation',
	UTILITIES = 'utilities',
	WHOLESALE_DISTRIBUTION = 'wholesaleDistribution',
}

export enum EMDFRequestActivityTypes {
	EVENT = 'prmtact001',
	DIGITAL_MARKETING = 'prmtact002',
	CONTENT_MARKETING = 'prmtact003',
	MISCELLANEOUS_MARKETING = 'prmtact004',
}

export enum EMDFRequestActivityTactics {
	IN_PERSON_NETWORKING_EVENT_SEMINAR_MEET_UP = 'prmtact001prmtct001',
	IN_PERSON_INDUSTRY_CONFERENCE_TRADE_SHOW = 'prmtact001prmtct002',
	WEBINAR = 'prmtact001prmtct003',
	EMAIL_CAMPAIGN = 'prmtact002prmtct001',
	DIGITAL_ADVERTISING_BANNER_AD = 'prmtact002prmtct002',
	PPC_OR_GOOGLE_ADS_CAMPAIGN = 'prmtact002prmtct003',
	SOCIAL_MEDIA_MARKETING = 'prmtact002prmtct004',
	SOCIAL_MEDIA_PAID_ADVERTISING = 'prmtact002prmtct005',
	BLOG_OR_ARTICLE = 'prmtact003prmtct001',
	BUSINESS_WHITE_PAPER_OR_EBOOK = 'prmtact003prmtct002',
	INFOGRAPHIC = 'prmtact003prmtct003',
	SOCIAL_CONTENT = 'prmtact003prmtct004',
	SUCCESS_STORY_CASE_STUDY = 'prmtact003prmtct005',
	TECHNICAL_WHITE_PAPER = 'prmtact003prmtct006',
	VIDEO = 'prmtact003prmtct007',
	WEB_CONTENT = 'prmtact003prmtct008',
	BROADCAST_ADVERTISING = 'prmtact004prmtct001',
	CAMPAIGN_WITH_INDUSTRY_PUBLICATION = 'prmtact004prmtct002',
	CO_BRANDED_MERCHANDISE = 'prmtact004prmtct003',
	DIRECT_MAIL = 'prmtact004prmtct004',
	LIST_PURCHASE = 'prmtact004prmtct005',
	OUTBOUND_TELEMARKETING_SALES = 'prmtact004prmtct006',
	PRINT_ADVERTISING = 'prmtact004prmtct007',
	OTHER = 'prmtact002prmtct006prmtact003prmtct009prmtact004prmtct008',
}

export enum EMDFRequestActivityExpenseTypes {
	BROADCAST_ADVERTISING = 'broadcastAdvertising',
	COBRANDED_MERCHANDISE = 'cobrandedMerchandise',
	CONTENT_CREATION = 'contentCreation',
	DIGITAL_ADVERTISING = 'digitalAdvertising',
	DIGITAL_MARKETING = 'digitalMarketing',
	DIRECT_MAIL = 'directMail',
	EMAIL_MARKETING = 'emailMarketing',
	FOOD_AND_BEVERAGE = 'foodAndBeverage',
	OTHER = 'other',
	OUTBOUND_TELESALES = 'outboundTelesales',
	PRINT_ADVERTISING = 'printAdvertising',
	PROSPECT_LIST_PURCHASE = 'prospectListPurchase',
	ROOM_RENTAL = 'roomRental',
	SOCIAL_MARKETING = 'socialMarketing',
	SPONSORSHIP_FEE = 'sponsorshipFee',
}

export enum ERegularRoles {
	CHANNEL_OPERATIONS_MANAGER = 'Channel Operations Manager (COM)',
}

export const PARTNER_SITE_FRIENLY_URL_PATH = '/web/liferay-partner';
