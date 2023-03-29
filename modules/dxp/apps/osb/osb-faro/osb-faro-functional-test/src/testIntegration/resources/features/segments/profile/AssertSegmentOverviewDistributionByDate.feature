@spira_Segments @component_Segments @team_FARO @priority_3
Feature: Segment Overview distribution filtered by date
	As a Business User, I should be able to filter segment overview by date property

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Distribution card filtered by date
		Given I click "everybody" in the table
		When I create breakdown context "Individuals" by "birthDate" named Birth Date
		And I click the "Explore Breakdown" button
		And I click row number "2" in the bar graph table
		Then I should see individual named "Derick Tremblay" in the table
		And I click the "Overview" tab
		And I delete the breakdown