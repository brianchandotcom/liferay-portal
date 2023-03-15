@spira_Data_Source @Data_Source @DXP @Token @team_FARO @priority_4
Feature: Sync Contacts by User Group using Auth Token
	As a Business User, I should be able to sync contacts by User Group using Token Authentication

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

	Scenario: Sync Contacts by User Group - Auth Token
		Given I go to the "Synced Contacts" DXP Page
		And I sync the Data Source Contacts by the following User Groups:
			| Irvine |
		And I go to the "Synced Sites" DXP Page
		And I create a new Combined Property with the following Sites on DXP:
			| Liferay DXP | Main Site | Second Site |
		And I create a dummy page called "Test Page" on the "Main Site" DXP Site
		And I generate page views on the following pages as "ryan.weng" on the "Main Site" DXP Site
			| Test Page |
		And I generate page views on the following pages as "nack.ho" on the "Main Site" DXP Site
			| Test Page |
		And I go to the "Home" page
		And I login as "test@faro.io:test"
		And I click the Property Dropdown Menu
		And I click "Liferay DXP Combined Property" in the Property Dropdown Menu
		And I go to the "Individuals" page
		And I click the "Known Individuals" tab
		When I search for "ryan.weng@test.com"
		Then I should see an item named "Ryan Weng" in the table
		When I search for "nack.ho@test.com"
		Then I should see an item named "Nack Ho" in the table
		When I search for "shrall.tang@test.com"
		Then I should not see an item named "Shrall Tang" in the table