@spira_Segments @Segments @team_FARO @priority_3
Feature: Segment Overview distribution filtered by boolean
	As a Business User, I should be able to filter segment overview by boolean property

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Distribution card filtered by boolean
		Given I click "everybody" in the table
		When I create breakdown context "Individuals" by "doNotCall" named Call
		Then I should see the following names in "Call" breakdown:
			| true  |
			| false |
		And I delete the breakdown