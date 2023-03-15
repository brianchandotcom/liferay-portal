@spira_Segments @Segments @Static @Creation @team_FARO @priority_5 @prototype
Feature: Create a Static Accounts Segment
	As a Business User, I should be able to create a Static Accounts Segment

	Background: [Setup] Navigate to Accounts Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Accounts" in the sidebar
		* I should see the "Accounts" page
		* I click the "Create Segment" button
		* I click the "Accounts Segment" dropdown option

	Scenario: Create the Static Accounts Segment
		Given I click the "Static Segment" dropdown option
		And I click the "Add Members" button
		And I search for "Reichel"
		When I click the checkbox on the table row containing "Alfonzo Reichel"
		Then I should only see Accounts named "Alfonzo" in the table
		And I name the Static segment "CreateStaticAccountsSegment - ${Random.1}" and save it
		When I go to the "Segments" page
		And I search for "CreateStaticAccountsSegment - ${Random.1}"
		Then I should see a "Segment" named "CreateStaticAccountsSegment - ${Random.1}" with "1" items