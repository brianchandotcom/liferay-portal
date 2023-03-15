@spira_Data_Source @Data_Source @DXP @Token @Property @team_FARO @priority_4 @prototype
Feature: Sync a DXP Site as a new Property
	As a Business User, I should be able to sync a DXP Site as a new Property

	Background: [Setup] Connect a DXP Data Source via Token
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

	Scenario: Sync a DXP Site as a new Property
		Given I go to the "Synced Sites" DXP Page
		When I create a new Combined Property with the following Sites on DXP:
			| Liferay DXP |
		And I go to the "Home" page
		And I login as "test@faro.io:test"
		And I go to the "Properties" page
		Then I should see a Property named "Liferay DXP Combined Property" in the table