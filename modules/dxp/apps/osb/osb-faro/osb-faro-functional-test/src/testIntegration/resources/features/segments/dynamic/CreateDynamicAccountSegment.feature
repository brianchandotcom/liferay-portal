@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_5
Feature: Create a Dynamic Accounts Segment
	As a Business User, I should be able to create a Dynamic Accounts Segment

	Background: [Setup] Navigate to Accounts Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option

	Scenario: Create the Dynamic Accounts Segment
		Given I select "Account Attributes" from the criterion type dropdown
		When I create a criteria with the following conditions:
			| accountName | contains | Zulauf-Davis |
		And I name the Dynamic segment "CreateDynamicAccountsSegment - ${Random.1}" and save it
		When I go to the "Segments" page
		And I search for "CreateDynamicAccountsSegment - ${Random.1}"
		Then I should see a "Segment" named "CreateDynamicAccountsSegment - ${Random.1}" with "7" items