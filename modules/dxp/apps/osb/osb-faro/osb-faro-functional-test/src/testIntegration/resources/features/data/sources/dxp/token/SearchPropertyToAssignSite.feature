@spira_Data_Source @Data_Source @DXP @Token @Property @Search @team_FARO @priority_3 @prototype
Feature: Search for a property when assigning sites to a property
	As a Business User, I should be able to search for a property when assigning sites

	Background: [Setup] Create a Property
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Properties" page
		* I create a new Property named "Search Property"
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Liferay DXP" button
		* I click the "Connect with Token" button
		* I copy the DXP Authentication Token
		* I set up the local DXP instance
		* I connect Analytics Cloud to DXP using the Authentication Token

	Scenario: Search for property
		Given I go to the "Synced Sites" DXP Page
		When I search for "Search Property" in DXP
		Then I should see a property named "Search Property" in DXP