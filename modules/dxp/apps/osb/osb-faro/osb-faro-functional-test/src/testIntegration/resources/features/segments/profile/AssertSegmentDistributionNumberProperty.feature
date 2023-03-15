@spira_Segments @Segments @team_FARO @priority_3
Feature: Segment distribution chart can be filtered by number property
	As a Business User, I should be able to filter distribution by number property

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Chart filtered by number property
		Given I click "everybody" in the table
		And I click the "Distribution" tab
		When I select breakdown "Accounts" by "annualRevenue" with number of bins 5
		Then I click row number "1" in the bar graph table
		And I should see Account named "Murazik, Lesch and Hyatt" in the table
		And I should see Account named "Quigley-Larkin" in the table