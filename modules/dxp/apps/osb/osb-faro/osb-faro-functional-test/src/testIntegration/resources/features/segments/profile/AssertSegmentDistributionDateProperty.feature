@spira_Segments @Segments @team_FARO @priority_3
Feature: Segment distribution chart can be filtered by date property
	As a Business User, I should be able to filter distribution by date property

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Chart filtered by date property
		Given I click "everybody" in the table
		And I click the "Distribution" tab
		When I select breakdown "Individuals" by "birthDate"
		And I click row number "2" in the bar graph table
		Then I should see an individual named "Derick Tremblay" in the table