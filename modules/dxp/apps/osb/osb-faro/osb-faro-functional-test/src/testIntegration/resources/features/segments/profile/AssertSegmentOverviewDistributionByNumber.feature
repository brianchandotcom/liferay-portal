@spira_Segments @component_Segments @team_FARO @priority_3
Feature: Segment Overview distribution filtered by number
	As a Business User, I should be able to filter segment overview by number property

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Distribution card filtered by number
		Given I click "everybody" in the table
		When I create breakdown context "Accounts" by "annualRevenue" with bin number 2 named Revenue
		Then I should see the following names in "Revenue" breakdown:
			| 3500000  |
			| 49250000 |
			| 95000000 |
		And I delete the breakdown