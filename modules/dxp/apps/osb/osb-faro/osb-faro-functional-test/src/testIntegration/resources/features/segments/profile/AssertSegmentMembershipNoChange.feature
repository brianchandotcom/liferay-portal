@spira_Segments @Segments @team_FARO @priority_3
Feature: No change on segment profile selected point
	As a Business User, I should be able to view text with no items found

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: No items found message
		Given I click "everybody" in the table
		And I click the "Membership" tab
		When I click row number "2" in the bar graph table
		Then I should see text saying "Lavina Waelchi" on the page