@spira_Segments @Segments @Dynamic @team_FARO @priority_5 @prototype
Feature: Edit Accounts Segment Criteria
	As an Business User, I should be able to edit an Accounts Segment's Criteria

	Background: [Setup] Create a Static Accounts Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Accounts Segment" dropdown option
		* I create a criteria with the following conditions:
			| familyName | contains | west |
		* I name the Static segment "EditAccountsSegmentCriteria - ${Random.1}" and save it

	Scenario: Edit Accounts Segment Criteria
		Given I go to the "Segments" page
		And I click "EditAccountsSegmentCriteria - ${Random.1}" in the table
		When I click the "Edit Segment" button
		And I delete the criteria group in row "1"
		And I create an AND criteria with the following conditions:
			| familyName | contains     | west |
			| age        | greater than | 50   |
		And I click the "Save" button
		And I go to the "Segments" page
		And I search for "EditAccountsSegmentCriteria - ${Random.1}"
		Then I should see a "Segment" named "EditAccountsSegmentCriteria - ${Random.1}" with "3" items