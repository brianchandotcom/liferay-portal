@spira_Data_Source @Data_Source @DXP @Token @team_FARO @priority_4
Feature: Sync Contacts by Organization using Auth Token
	As a Business User, I should be able to sync contacts by Organization using Token Authentication

	Background: [Setup] Add the DXP Data Source via Auth Token
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Liferay DXP" button
		* I click the "Connect with Token" button
		* I copy the DXP Authentication Token
		* I set up the local DXP instance
		* I connect Analytics Cloud to DXP using the Authentication Token

	Scenario: Sync Contacts by Organization - Auth Token
		Given I go to the "Synced Contacts" DXP Page
		And I sync the Data Source Contacts by the following Organizations:
			| Riot |
		And I go to the "Synced Sites" DXP Page
		And I create a new Combined Property with the following Sites on DXP:
			| Liferay DXP | Main Site | Second Site |
		And I create a dummy page called "OrgTokenContacts" on the "Main Site" DXP Site
		And I generate page views on the following pages as "josh.day" on the "Main Site" DXP Site
			| OrgTokenContacts |
		And I go to the "Home" page
		And I login as "test@faro.io:test"
		And I click the Property Dropdown Menu
		And I click "Liferay DXP Combined Property" in the Property Dropdown Menu
		And I go to the "Individuals" page
		And I click the "Known Individuals" tab
		When I search for "josh.day@test.com"
		Then I should see an item named "Josh Day" in the table
		When I search for "shrall.tang@test.com"
		Then I should not see an item named "Shrall Tang" in the table